package de.deutscherv.gq0500.offenestellenassistent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class EmbeddingConfig {

    // Inject values from application.properties
    @Value("${spring.ai.ollama.base-url}")
    private String baseUrl;

    @Value("${spring.ai.ollama.embedding.model}")
    private String embeddingModelName;

    @Bean
    public EmbeddingModel embeddingModel() {

        OllamaOptions options = OllamaOptions.builder().model(embeddingModelName).build();

        OllamaEmbeddingModel embedding =  OllamaEmbeddingModel.builder().ollamaApi(
                OllamaApi.builder()
                        .baseUrl(baseUrl)
                        .build()
        ).defaultOptions(options).build();

        sanityCheck(embedding);

        return embedding;
    }

    private static void sanityCheck(OllamaEmbeddingModel embedding) {
        // --- Sanity check ---
        try {
            float[] testEmbedding = embedding.embed(new Document("Dies ist ein Test"));
            if (testEmbedding == null || testEmbedding.length == 0) {
                throw new RuntimeException("Embedding returned empty result. Check Ollama configuration.");
            }

            log.info("Embedding check successful: length={}", testEmbedding.length);
        } catch (Exception e) {
            log.error("Embedding model sanity check failed.", e);
            throw new RuntimeException("Failed to generate embedding. Check Ollama configuration.", e);
        }

        log.info("Embedding model sanity check passed.");
    }
}
