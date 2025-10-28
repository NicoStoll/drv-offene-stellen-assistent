package de.deutscherv.gq0500.offenestellenassistent;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromptRequestModel {
    
    private String prompt;

    private String conversationId;
}
