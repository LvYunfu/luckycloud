package org.luckycloud.ai.service.impl;

import jakarta.annotation.Resource;
import org.luckycloud.ai.domain.AIRequest;
import org.luckycloud.ai.service.AISupportService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

/**
 * @author lvyf
 * @description:
 * @date 2026/5/13
 */
@Service
public class AISupportServiceImpl implements AISupportService {


    @Resource
    private ChatClient chatClient;


    @Override
    public String generateAnswer(AIRequest aiRequest) {
        return chatClient.prompt()
                .system(aiRequest.getSystemPrompt())
                .user(aiRequest.getQuestion())
                .call()
                .content();
    }

    @Override
    public <T> T generateJsonEntity(AIRequest aiRequest, Class<T> clazz) {
        return chatClient.prompt()
                .system(aiRequest.getSystemPrompt())
                .user(aiRequest.getQuestion())
                .call()
                .entity(clazz);
    }

    @Override
    public <T> T generateStructuredEntity(AIRequest aiRequest, ParameterizedTypeReference<T> typeReference) {
        return chatClient.prompt()
                .system(aiRequest.getSystemPrompt())
                .user(aiRequest.getQuestion())
                .call()
                .entity(typeReference);
    }
}
