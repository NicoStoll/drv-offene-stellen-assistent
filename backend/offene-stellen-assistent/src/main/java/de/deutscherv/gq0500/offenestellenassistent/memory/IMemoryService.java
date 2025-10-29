package de.deutscherv.gq0500.offenestellenassistent.memory;

import org.springframework.ai.chat.messages.Message;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IMemoryService {

    List<Message> getChatHistory(@Nullable String conversationId);

    String addUserMessage(@Nullable String conversationId, String content);

    String addAssistantMessage(@Nullable String conversationId, String content);
}
