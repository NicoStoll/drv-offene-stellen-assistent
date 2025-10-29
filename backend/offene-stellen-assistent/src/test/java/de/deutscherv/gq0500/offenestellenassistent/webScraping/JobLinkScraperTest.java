package de.deutscherv.gq0500.offenestellenassistent.webScraping;

import de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper.JobLinkScraper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Slf4j
public class JobLinkScraperTest {

    @Autowired
    private JobLinkScraper jobLinkScraper;

    @Test
    public void testScrapeJobLink() {
        List<String> links = jobLinkScraper.fetchAllListingLinks();
        log.atInfo().log("Open Job offers: {}", links.size());
        links.forEach(link -> log.atInfo().log(link));
        assertThat(links.size(), is(greaterThan(160)));
    }
}
