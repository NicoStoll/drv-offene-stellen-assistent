package de.deutscherv.gq0500.offenestellenassistent;

import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
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

                        Kontextinformation befindet sich direkt darunter:

                        ---------------------
                        <question_answer_context>
                        ---------------------

                        Beantworte die folgende Frage basierend auf den obigen Kontextinformationen.

                        Befolge folgende Regeln:
                        1. Wenn die Anwort nicht mit den Infos beantwortet werden kann, sage "Ich kann die Frage nicht beantworten."
                        2. Nutze keine Statements wie "Basierend auf dem Kontext...".
                        3. Antworte präzise und auf deutsch
                        4. Du bist ein Helfer für Arbeitsuchende, die eine offene Stelle bei der DRV suchen.
                        """)
                .build();

        return QuestionAnswerAdvisor.builder(vectorStore)
                .promptTemplate(customPromptTemplate)
                .build();
    }
}
