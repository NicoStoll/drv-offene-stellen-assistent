package de.deutscherv.gq0500.offenestellenassistent.memory;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMemoryService implements IMemoryService{

    private final ChatMemory chatMemory;

    @Override
    public List<Message> getChatHistory(@Nullable String conversationId) {
        if (conversationId == null || conversationId.isEmpty()) {
            return List.of();
        }
        return chatMemory.get(conversationId);
    }

    @Override
    public String addUserMessage(@Nullable String conversationId, String content) {
        return addMessage(conversationId, content, true);
    }

    @Override
    public String addAssistantMessage(@Nullable String conversationId, String content) {
        return addMessage(conversationId, content, false);
    }

    private String addMessage(@Nullable String conversationId, String content, boolean isUser) {
        if (conversationId == null || conversationId.isEmpty()) {
            conversationId = UUID.randomUUID().toString();
        }
        Message message = isUser ? new UserMessage(content) : new AssistantMessage(content);
        chatMemory.add(conversationId, message);
        return conversationId;
    }
}

