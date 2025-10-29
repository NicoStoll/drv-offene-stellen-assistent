package de.deutscherv.gq0500.offenestellenassistent.webController;

import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobLinkScraper;
import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobOfferScraper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

        List<String> links = jobLinkScraper.fetchAllListingLinks();
        links.stream().map(link -> jobOfferScraper.scrapeOpenJobOffer(link)).map(jobOffer -> embeddingModel.embed(jobOffer.toString()));

        log.atInfo().log(links.getFirst());

        return "Filled Context from " + links.size() + " job offers";
    }
}
