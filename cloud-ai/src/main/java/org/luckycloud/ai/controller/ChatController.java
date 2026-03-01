package org.luckycloud.ai.controller;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.luckycloud.ai.dto.ChatRequest;
import org.luckycloud.ai.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author lvyf
 * @description:
 * @date 2026/2/27
 */

@RestController
@RequestMapping("/ai")
public class ChatController {


    @Resource
    private ChatService chatService;

    @PostMapping("/chat")
    public Flux<String> chat(ChatRequest request) {
       return chatService.chat(request);

    }
}
