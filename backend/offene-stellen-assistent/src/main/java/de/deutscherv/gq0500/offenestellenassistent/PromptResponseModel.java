package de.deutscherv.gq0500.offenestellenassistent;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromptResponseModel {

    private String messageId;

    private String response;

    private String conversationId;
}
