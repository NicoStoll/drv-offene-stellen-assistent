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

    public String getValueByLabel(org.jsoup.nodes.Document doc, String label) {
        Element box = doc.selectFirst(
                ".jobAdMIDtlContainer .jobAdMItextCnt:has(p.globalText.bold:matches(^\\s*"
                        + Pattern.quote(label) + "\\s*$))"
        );
        if (box == null) {
            return null;
        }

        org.jsoup.select.Elements valueNodes = box.select(
                "> .globalText:not(.bold), > .field__item, > .field > .field__item"
        );

        if (valueNodes.isEmpty()) {
            // Fallback
            org.jsoup.nodes.Element copy = box.clone();
            copy.select("p.globalText.bold").remove();
            return htmlNormalizer.normalizeLines(htmlNormalizer.extractTextWithLineBreaks(copy));
        }

        java.util.List<String> parts = valueNodes.stream()
                .map(e -> htmlNormalizer.htmlToReadableText(e))
                .map(t -> htmlNormalizer.normalizeLines(t))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .distinct()
                .collect(java.util.stream.Collectors.toList());

        if (parts.isEmpty()) return null;
        if (parts.size() == 1) return parts.getFirst();

        return String.join("\n", parts);
    }

    public String sectionText(Document doc, String h2Text) {
        Element fieldItem = doc.selectFirst(
                ".textAreaContent:has(h2.jobTextHeader:matches(^\\s*" + Pattern.quote(h2Text) + "\\s*$)) " +
                        ".jobTextPara .field__item"
        );
        if (fieldItem == null) return null;

        return htmlNormalizer.normalizeLines((htmlNormalizer.htmlToReadableText(fieldItem)));
    }

    public String extractFurtherInformation(Document doc) {
        Elements items = doc.select(
                ".textAreaContent:has(h2.jobTextHeader:matches(^\\s*Weitere Informationen\\s*$)) .field__item"
        );
        if (items.isEmpty()) return null;

        return items.stream()
                .map(htmlNormalizer::htmlToReadableText)
                .map(htmlNormalizer::normalizeLines)
                .collect(Collectors.joining("\n\n"))
                .trim();
    }

    public String textOrNull(Element e) {
        return e == null ? null : htmlNormalizer.normalizeSpaces(e.text());
    }
}
