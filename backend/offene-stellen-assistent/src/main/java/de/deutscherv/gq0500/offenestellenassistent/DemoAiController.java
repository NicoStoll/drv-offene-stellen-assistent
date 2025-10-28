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

    @GetMapping
    public String ask(@RequestBody PromptRequestModel prompt) {
        return this.chatClient
                .prompt()
                .user(prompt.getPrompt())
                .call()
                .content();
    }
}
