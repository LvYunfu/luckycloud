package org.luckycloud.ai.service;

import org.luckycloud.ai.dto.ChatRequest;
import reactor.core.publisher.Flux;

/**
 * @author lvyf
 * @description:
 * @date 2026/2/27
 */
public interface ChatService {
    Flux<String> chat(ChatRequest request);
}
