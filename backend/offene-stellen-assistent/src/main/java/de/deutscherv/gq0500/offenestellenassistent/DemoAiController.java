package de.deutscherv.gq0500.offenestellenassistent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ask")
public class DemoAiController {

    private final ChatClient chatClient;

    public DemoAiController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping
    public String ask(@RequestParam String prompt) {
        return this.chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}
