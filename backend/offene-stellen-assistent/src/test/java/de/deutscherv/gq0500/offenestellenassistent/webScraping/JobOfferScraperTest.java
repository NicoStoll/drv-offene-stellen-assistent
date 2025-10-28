package de.deutscherv.gq0500.offenestellenassistent.webScraping;

import de.deutscherv.gq0500.offenestellenassistent.webScraping.models.JobOffer;
import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobOfferScraper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JobOfferScraperTest {

    @Autowired
    JobOfferScraper jobOfferScraper;

    @Test
    public void jobOfferScraperTest(){
       JobOffer jobOffer = jobOfferScraper.scrapeOpenJobOffer("https://drv-bund-karriere.de/jobs/oracle-administrator-berlin");
       System.out.println(jobOffer);
    }
}
