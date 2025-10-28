package de.deutscherv.gq0500.offenestellenassistent.webScraping;

import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobLinkScraper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class JobLinkScraperTest {

    @Autowired
    private JobLinkScraper jobLinkScraper;

    @Test
    public void testScrapeJobLink() {
        List<String> links = jobLinkScraper.fetchAllListingLinks();
        System.out.println("Open Job offers: " + links.size());
        links.forEach(System.out::println);
        assertThat(links.size(), is(165));
    }
}
