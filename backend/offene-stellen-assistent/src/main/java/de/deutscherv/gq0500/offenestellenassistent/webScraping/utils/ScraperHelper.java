package de.deutscherv.gq0500.offenestellenassistent.webScraping.utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class ScraperHelper {

    @Autowired
    private HtmlNormalizer htmlNormalizer;

    public String getValueByLabel(Document doc, String label) {
        // Primär: exakte Label-Box finden
        Element box = doc.selectFirst(
                ".jobAdMIDtlContainer .jobAdMItextCnt:has(p.globalText.bold:matches(^\\s*"
                        + Pattern.quote(label) + "\\s*$))"
        );
        if (box == null) {
            return null;
        }
        // Alles außer dem Label selbst als „Wert“
        // Einige Werte liegen in <div.globalText>, andere (z. B. Ausschreibungsnummer) in .field__item
        Elements valueNodes = box.select("> .globalText:not(.bold), .field__item");
        if (valueNodes.isEmpty()) {
            // Fallback: nimm den ganzen Block ohne das Label, bereinigt
            Element copy = box.clone();
            copy.select("p.globalText.bold").remove();
            return htmlNormalizer.normalizeLines(htmlNormalizer.extractTextWithLineBreaks(copy));
        }

        // HTML der Value-Nodes zu Text mit bewahrten <br> und Listen umbrechen
        String merged = valueNodes.stream()
                .map(e->htmlNormalizer.htmlToReadableText(e))
                .collect(Collectors.joining("\n"))
                .trim();

        return htmlNormalizer.normalizeLines(merged);
    }

    public String sectionText(Document doc, String h2Text) {
        Element fieldItem = doc.selectFirst(
                ".textAreaContent:has(h2.jobTextHeader:matches(^\\s*" + Pattern.quote(h2Text) + "\\s*$)) " +
                        ".jobTextPara .field__item"
        );
        if (fieldItem == null) return null;

        return htmlNormalizer.normalizeLines((htmlNormalizer.htmlToReadableText(fieldItem)));
    }

    public String textOrNull(Element e) {
        return e == null ? null : htmlNormalizer.normalizeSpaces(e.text());
    }
}
