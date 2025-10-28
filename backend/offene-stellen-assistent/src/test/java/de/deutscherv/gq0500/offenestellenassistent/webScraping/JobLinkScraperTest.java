package de.deutscherv.gq0500.offenestellenassistent.webScraping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class JobLinkScraperTest {

    @Autowired
    private JobLinkScraper jobLinkScraper;

    @Test
    public void testScrapeJobLink() throws IOException {
        List<String> links = jobLinkScraper.fetchAllListingLinks();
        System.out.println("Open Job offers: " + links.size());
        links.forEach(System.out::println);
    }
}
