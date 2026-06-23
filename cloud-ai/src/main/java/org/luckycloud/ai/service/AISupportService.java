package org.luckycloud.ai.service;

import org.luckycloud.ai.domain.AIRequest;
import org.springframework.core.ParameterizedTypeReference;

/**
 * @author lvyf
 * @description:
 * @date 2026/5/13
 */
public interface AISupportService {


    String generateAnswer(AIRequest aiRequest);

    <T> T generateJsonEntity(AIRequest aiRequest, Class<T> clazz);

    <T> T generateStructuredEntity(AIRequest aiRequest, ParameterizedTypeReference<T> typeReference);
}
