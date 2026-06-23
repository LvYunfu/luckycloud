package org.luckycloud.ai.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lvyf
 * @description:
 * @date 2026/5/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIRequest {

    private String systemPrompt;

    private String question;
}
