package de.deutscherv.gq0500.offenestellenassistent.webScraping.utils;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Component;

@Component
public class HtmlNormalizer {
    public String normalizeLines(String s) {
        if (s == null) return null;

        String t = s.replace('\u00A0', ' ')
                .replaceAll("[ \\t]+", " ")
                .replaceAll(" *\n *", "\n")
                .replaceAll("\n{2,}", "\n")
                .trim();
        return t;
    }

    public String normalizeSpaces(String s) {
        if (s == null) return null;
        return s.replace('\u00A0', ' ').replaceAll("[ \\t]+", " ").trim();
    }

    public String extractTextWithLineBreaks(Element node) {
        Element copy = node.clone();
        copy.select("br").forEach(br -> br.after(new TextNode("\n")));
        String raw = copy.html().replaceAll("(?is)<[^>]+>", "");
        String decoded = Parser.unescapeEntities(raw, false);

        return normalizeLines(decoded);
    }

    public String htmlToReadableText(Element node) {
        // Arbeitskopie
        Element copy = node.clone();

        // <br> zu Newlines
        copy.select("br").forEach(br -> br.after(new TextNode("\n")));

        // Listenpunkte hübsch formatieren
        copy.select("ul, ol").forEach(list -> {
            boolean ordered = list.normalName().equals("ol");
            int[] counter = {1};
            list.select("> li").forEach(li -> {
                String bullet = ordered ? (counter[0]++) + ". " : "- ";
                li.prependText(bullet);
                li.appendChild(new TextNode("\n"));
            });
        });

        // Absätze & Überschriften trennen
        copy.select("p, h1, h2, h3, h4, h5, h6").forEach(el -> {
            el.appendChild(new TextNode("\n"));
        });

        String withBreaks = copy.html().replaceAll("(?is)<[^>]+>", "");

        String decoded = Parser.unescapeEntities(withBreaks, false);

        return normalizeLines(decoded);
    }
}
