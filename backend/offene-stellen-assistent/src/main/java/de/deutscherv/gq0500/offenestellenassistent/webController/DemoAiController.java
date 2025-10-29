package de.deutscherv.gq0500.offenestellenassistent.webController;

import de.deutscherv.gq0500.offenestellenassistent.PromptRequestModel;
import de.deutscherv.gq0500.offenestellenassistent.PromptResponseModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
