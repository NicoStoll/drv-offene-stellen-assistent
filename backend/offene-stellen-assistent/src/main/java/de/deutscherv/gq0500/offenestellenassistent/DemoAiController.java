package de.deutscherv.gq0500.offenestellenassistent;

import de.deutscherv.gq0500.offenestellenassistent.memory.ChatMemoryService;
import de.deutscherv.gq0500.offenestellenassistent.memory.IMemoryService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ask")
public class DemoAiController {

    private final ChatClient chatClient;

    private final IMemoryService chatMemory;

    public DemoAiController(ChatClient.Builder builder, IMemoryService chatMemory) {
        this.chatClient = builder
                .build();

        this.chatMemory = chatMemory;
    }

    @PostMapping
    public PromptResponseModel ask(@RequestBody PromptRequestModel prompt) {

        List<Message> history = this.chatMemory.getChatHistory(prompt.getConversationId());

        String conversationId = this.chatMemory.addUserMessage(prompt.getConversationId(), prompt.getPrompt());

        String response = this.chatClient
                .prompt()
                .messages(history)
                .user(prompt.getPrompt())
                .call()
                .content();

        this.chatMemory.addAssistantMessage(conversationId, response);

        return new PromptResponseModel(response, conversationId);
    }
}
