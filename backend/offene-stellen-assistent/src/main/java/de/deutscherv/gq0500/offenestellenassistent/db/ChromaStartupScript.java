package de.deutscherv.gq0500.offenestellenassistent.db;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class ChromaStartupScript {


    private final VectorStore vectorStore;

    public ChromaStartupScript(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void initChroma() {
        sanityCheck();
        sanityCheck2();
    }

    private void sanityCheck2() {
        // === Second sanity check: count all documents in the collection ===
        List<Document> allDocs = this.vectorStore.similaritySearch(
                SearchRequest.builder().query("").topK(1000).build() // assuming <1000 docs
        );
        log.info("Total number of documents in the database: {}", allDocs.size());
    }

    private void sanityCheck() {

        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("meta1", "meta1")),
                new Document("The World is Big and Salvation Lurks Around the Corner"),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("meta2", "meta2")));

        // Add the documents
        vectorStore.add(documents);

        // Retrieve documents similar to a query
        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query("Spring").topK(5).build());
        log.info("Found {} documents.", results.size());

        List<String> docIds = documents.stream()
                .map(Document::getId)
                .toList();

        vectorStore.delete(docIds);
        log.info("Removed {} documents after sanity check.", docIds.size());
    }
}
