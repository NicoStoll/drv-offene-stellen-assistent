package de.deutscherv.gq0500.offenestellenassistent.webScraping;

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

    @Value("${baseUrl_JobsDrv}")
    private String baseUrl;

    @Value("${jobLinkScraper.timeout}")
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

            // read all "Mehr erfahren" Links
            doc.select("a:matchesOwn(^\\s*Mehr erfahren\\s*$)")
                    .forEach(a -> links.add(a.attr("abs:href")));

            // next Link für pagination
            Element next = doc.selectFirst("a:matchesOwn(^\\s*Nächste Seite\\s*$)");
            url = (next != null) ? next.attr("abs:href") : null;
        }

        return links;
    }
}