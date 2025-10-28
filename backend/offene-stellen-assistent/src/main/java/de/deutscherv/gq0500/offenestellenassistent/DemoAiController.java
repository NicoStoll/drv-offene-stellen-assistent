package de.deutscherv.gq0500.offenestellenassistent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ask")
public class DemoAiController {

    private final ChatClient chatClient;

    public DemoAiController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping
    public PromptResponseModel ask(@RequestBody PromptRequestModel prompt) {
        String response = this.chatClient
                .prompt()
                .user(prompt.getPrompt())
                .call()
                .content();

        return new PromptResponseModel(response);
    }
}
