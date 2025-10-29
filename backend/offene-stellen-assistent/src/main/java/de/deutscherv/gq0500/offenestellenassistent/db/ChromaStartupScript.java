package de.deutscherv.gq0500.offenestellenassistent.db;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chroma.vectorstore.ChromaApi;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class ChromaStartupScript {

    private final ChromaApi chromaApi;

    private final VectorStore vectorStore;
    @Value("${spring.ai.vectorstore.chroma.tenant-name}")
    private String tenantName;
    @Value("${spring.ai.vectorstore.chroma.database-name}")
    private String databaseName;
    @Value("${chroma.collection-name}")
    private String collectionName;

    public ChromaStartupScript(ChromaApi chromaApi, VectorStore vectorStore) {
        this.chromaApi = chromaApi;
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void initChroma() {
        // Create database if it doesn't exist
        try {
            final String db = chromaApi.getDatabase(tenantName, databaseName).name();
            log.info(db);
        } catch (Exception e) {
            chromaApi.createDatabase(tenantName, databaseName);
        }

        try {
            chromaApi.getCollection(tenantName, databaseName, collectionName);
        } catch (Exception e) {
            chromaApi.createCollection(tenantName, databaseName, new ChromaApi.CreateCollectionRequest(collectionName));
        }

        sanityCheck();
    }

    private void sanityCheck() {

        List<Document> documents = List.of(new Document(
                                                   "Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!",
                                                   Map.of("meta1", "meta1")), new Document("The World is Big and Salvation Lurks Around the Corner"),
                                           new Document(
                                                   "You walk forward facing the past and you turn back toward the future.",
                                                   Map.of("meta2", "meta2")));

        // Add the documents
        vectorStore.add(documents);

        // Retrieve documents similar to a query
        List<Document> results = this.vectorStore.similaritySearch(
                SearchRequest.builder().query("Spring").topK(5).build());
        log.info("Found {} documents.", results.size());

        List<String> docIds = documents.stream().map(Document::getId).toList();

        vectorStore.delete(docIds);
        log.info("Removed {} documents after sanity check.", docIds.size());
    }
}
