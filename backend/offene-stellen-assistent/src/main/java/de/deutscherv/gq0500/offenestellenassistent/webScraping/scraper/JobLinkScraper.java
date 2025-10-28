package de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JobLinkScraper {

    @Value("${jobsDrv.baseUrl}")
    private String baseUrl;

    @Value("${job-link-scraper.timeout}")
    private int timeOut;

    @SneakyThrows(IOException.class)
    public List<String> fetchAllListingLinks() {
        List<String> links = new ArrayList<>();
        String url = baseUrl;

        while (url != null) {
            Document doc = Jsoup.connect(url)
                    .userAgent("DRV-Scraper/1.0 (+contact: you@domain)")
                    .referrer("https://www.google.com")
                    .timeout(timeOut)
                    .get();
            // all links on current page
            List<String> currentLinks = doc.select("div.resultItem div.resultItemTop h3.globalHeader3 a[href]")
                    .eachAttr("abs:href");

            links.addAll(currentLinks);

            // Navigate to next page
            Element nextButton = doc.selectFirst("li.pager__item--next a[href]");
            if (nextButton == null) {
                break;
            } else {
                String nextHref = nextButton.attr("href");
                url = baseUrl + nextHref;
            }
        }
        return links;
    }
}