package de.deutscherv.gq0500.offenestellenassistent;

import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RagConfig {


    @Bean
    public QuestionAnswerAdvisor questionAnswerAdvisor(VectorStore vectorStore) {

        PromptTemplate customPromptTemplate = PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder()
                        .startDelimiterToken('<')
                        .endDelimiterToken('>')
                        .build())
                .template("""
                        <query>
                        
                        Die folgenden Kontextinformationen dienen als Grundlage für deine Antwort:
                        
                        ---------------------
                        <question_answer_context>
                        ---------------------
                        
                        Beantworte die Frage sorgfältig auf Basis der oben angegebenen Kontextinformationen.
                        
                        Regeln:
                        1. Nutze ausschließlich die Informationen aus dem Kontext, falls sie relevant sind.
                        2. Wenn der Kontext keine ausreichenden Informationen enthält, gib trotzdem dein Bestes:
                           - Formuliere eine plausible, hilfreiche Antwort basierend auf allgemein bekanntem Wissen über Bewerbungen bei der Deutschen Rentenversicherung Bund (DRV Bund).
                           - Falls du dir unsicher bist, erwähne dies höflich und schlage sinnvolle Rückfragen oder nächste Schritte vor.
                        3. Verwende klare, verständliche und natürliche deutsche Sprache.
                        4. Sprich die Nutzerin oder den Nutzer direkt und freundlich an.
                        5. Dein Ziel ist es, Arbeitssuchende bei der Bewerbung auf offene Stellen bei der Deutschen Rentenversicherung Bund zu unterstützen – z. B. durch Hinweise zu Bewerbungsprozessen, Anforderungen oder Formulierungen.
                        6. Vermeide Einleitungen wie „Basierend auf dem Kontext…“ oder „Laut den Informationen…“. Antworte direkt.
                        7. Wenn keine relevanten Informationen im Kontext stehen, sag nicht einfach „Ich kann die Frage nicht beantworten“, sondern biete stattdessen Unterstützung durch klärende Fragen oder allgemeine Hilfestellung.
                        
                        """)
                .build();

        return QuestionAnswerAdvisor.builder(vectorStore)
                .promptTemplate(customPromptTemplate)
                .build();
    }
}
