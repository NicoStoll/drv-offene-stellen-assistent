package de.deutscherv.gq0500.offenestellenassistent.webScraping.scraper;

import de.deutscherv.gq0500.offenestellenassistent.webScraping.models.JobOffer;
import de.deutscherv.gq0500.offenestellenassistent.webScraping.utils.ScraperHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JobOfferScraper {

    @Value("${job-link-scraper.timeout}")
    private Integer timeOut;

    @Autowired
    ScraperHelper helper;

    @SneakyThrows(IOException.class)
    public JobOffer scrapeOpenJobOffer(String url) {
        Document doc = Jsoup.connect(url)
                .userAgent("DRV-Scraper/1.0 (+contact: contact@deutscherv.de)")
                .referrer("https://www.google.com")
                .timeout(timeOut)
                .get();

        return parse(doc);
    }

    public JobOffer parse(Document doc) {
        JobOffer dto = new JobOffer();

        dto.setTitle(helper.textOrNull(doc.selectFirst("h1.jobAdMIHdr")));

        dto.setLocation(helper.getValueByLabel(doc, "Ort"));
        dto.setCompensation(helper.getValueByLabel(doc, "Vergütung"));
        dto.setEntryDate(helper.getValueByLabel(doc, "Eintrittsdatum"));
        dto.setTypeOfEmployment(helper.getValueByLabel(doc, "Beschäftigung"));
        dto.setApplicationDeadline(helper.getValueByLabel(doc, "Bewerbungsfrist"));
        dto.setTenderNumber(helper.getValueByLabel(doc, "Ausschreibungsnummer"));

        dto.setTasks(helper.sectionText(doc, "Aufgaben"));
        dto.setAreaOfActivity(helper.sectionText(doc, "Tätigkeitsbereich"));
        dto.setApplicationProfile(helper.sectionText(doc, "Profil"));
        dto.setFurtherInformation(helper.extractFurtherInformation(doc));

        log.atInfo().log("Found JobOffer: {}", dto.getTitle());

        return dto;
    }

}
