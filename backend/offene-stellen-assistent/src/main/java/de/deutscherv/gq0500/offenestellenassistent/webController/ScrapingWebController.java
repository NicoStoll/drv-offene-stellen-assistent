package de.deutscherv.gq0500.offenestellenassistent.webController;

import de.deutscherv.gq0500.offenestellenassistent.webScraping.models.JobOffer;
import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobLinkScraper;
import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobOfferScraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/updateContext")
@Slf4j
public class ScrapingWebController {

    @Autowired
    JobLinkScraper jobLinkScraper;

    @Autowired
    JobOfferScraper jobOfferScraper;

    @Qualifier("embeddingModel")
    @Autowired
    EmbeddingModel embeddingModel;

    @PostMapping
    public String updateContext() {
        long start = System.currentTimeMillis();
        AtomicInteger counter = new AtomicInteger(0);

        List<String> links = jobLinkScraper.fetchAllListingLinks().stream().peek(link -> log.atInfo().log("Found Link {}: {}", counter.getAndIncrement(), link)).toList();
        counter.set(0);
        List<JobOffer> jobOffers = links.stream().map(link -> jobOfferScraper.scrapeOpenJobOffer(link)).peek(jobOffer -> log.atInfo().log("Found JobOffer {}: {}", counter.getAndIncrement(), jobOffer.getTitle())).toList();
        counter.set(0);
        List<float[]> embeddings = jobOffers.stream()
                .peek(jobOffer -> log.atInfo().log("Embedding JobOffer {}: {}", counter.getAndIncrement(), jobOffer.getTitle()))
                .map(jobOffer -> embeddingModel.embed(jobOffer.toString()))
                .toList();

        double durationSeconds = (System.currentTimeMillis() - start) / 1000.0;
        log.atInfo().log("Embedding completed in {:.3f} seconds", durationSeconds);

        log.atInfo().log(Arrays.toString(embeddings.getFirst()));
        return "Filled Context from " + links.size() + " job offers\nDuration: " + durationSeconds;
    }
}
