package de.deutscherv.gq0500.offenestellenassistent.webController;

import de.deutscherv.gq0500.offenestellenassistent.webScraping.models.JobOffer;
import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobLinkScraper;
import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobOfferScraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    VectorStore vectorStore;

    @PostMapping
    public String updateContext() {
        long start = System.currentTimeMillis();
        AtomicInteger counter = new AtomicInteger(0);

        List<String> links = jobLinkScraper.fetchAllListingLinks()
                                           .stream()
                                           .peek(link -> log.atInfo()
                                                            .log("Found Link {}: {}", counter.getAndIncrement(), link))
                                           .toList();
        counter.set(0);
        List<JobOffer> jobOffers = links.stream()
                                        .map(link -> jobOfferScraper.scrapeOpenJobOffer(link))
                                        .peek(jobOffer -> log.atInfo()
                                                             .log("Found JobOffer {}: {}", counter.getAndIncrement(),
                                                                  jobOffer.getTitle()))
                                        .toList();
        counter.set(0);
        jobOffers.stream()
                 .peek(jobOffer -> log.atInfo()
                                      .log("Embedding JobOffer {}: {}", counter.getAndIncrement(), jobOffer.getTitle()))
                 .map(jobOffer -> new Document(jobOffer.toString(), jobOffer.toMap()))
                 .map(List::of)
                 .forEach(vectorStore::add);

        double durationSeconds = (System.currentTimeMillis() - start) / 1000.0;
        log.atInfo().log("Embedding completed in {} seconds", durationSeconds);

        return "Filled Context from " + links.size() + " job offers\nDuration: " + durationSeconds + " sec.";
    }
}
