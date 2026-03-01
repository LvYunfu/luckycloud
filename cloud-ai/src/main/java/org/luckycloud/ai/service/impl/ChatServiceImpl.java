package org.luckycloud.ai.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.luckycloud.ai.dto.ChatRequest;
import org.luckycloud.ai.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author lvyf
 * @description:
 * @date 2026/2/27
 */
@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatClient chatClient;

    @Override
    public Flux<String> chat(ChatRequest request) {
        return chatClient.prompt()
                .stream()
                .content();
    }
}

