package de.deutscherv.gq0500.offenestellenassistent.webController;

import de.deutscherv.gq0500.offenestellenassistent.PromptRequestModel;
import de.deutscherv.gq0500.offenestellenassistent.PromptResponseModel;
import de.deutscherv.gq0500.offenestellenassistent.memory.IMemoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/chat")
public class DemoAiController {

    private final ChatClient chatClient;

    private final IMemoryService chatMemory;

    private final QuestionAnswerAdvisor advisor;

    public DemoAiController(ChatClient.Builder builder, IMemoryService chatMemory, QuestionAnswerAdvisor advisor) {
        this.chatClient = builder
                .build();

        this.chatMemory = chatMemory;
        this.advisor = advisor;
    }

    @PostMapping
    public PromptResponseModel ask(@RequestBody PromptRequestModel prompt) {

        String messageId = UUID.randomUUID().toString();

        List<Message> history = this.chatMemory.getChatHistory(prompt.getConversationId());

        String conversationId = this.chatMemory.addUserMessage(prompt.getConversationId(), prompt.getPrompt());

        String response = this.chatClient
                .prompt()
                .advisors(advisor)
                .messages(history)
                .user(prompt.getPrompt())
                .call()
                .content();

        this.chatMemory.addAssistantMessage(conversationId, response);

        return new PromptResponseModel(messageId, response, conversationId);
    }

    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<PromptResponseModel> stream(
            @RequestParam String prompt,
            @RequestParam(required = false) String conversationId) {

        log.info("Received prompt from user: {} - Streaming result", prompt);

        String messageId = UUID.randomUUID().toString();

        List<Message> history = this.chatMemory.getChatHistory(conversationId);

        String conversationIdFinal = this.chatMemory.addUserMessage(conversationId, prompt);

        StringBuilder responseBuffer = new StringBuilder();

        return chatClient.prompt()
                .messages(history)
                .user(prompt)
                .advisors(advisor)
                .stream()
                .content()
                .doOnNext(responseBuffer::append)
                .doOnComplete(() -> {
                    String fullResponse = responseBuffer.toString();
                    this.chatMemory.addAssistantMessage(conversationIdFinal, fullResponse);
                    log.info("Saved assistant response to memory ({} chars)", fullResponse.length());
                })
                .map(chunk -> new PromptResponseModel(messageId, chunk, conversationIdFinal));
    }

}
