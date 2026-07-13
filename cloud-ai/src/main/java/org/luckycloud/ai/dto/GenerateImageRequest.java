package org.luckycloud.ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * AI 生成图片请求
 *
 * @author lvyf
 * @date 2026/7/6
 */
@Data
public class GenerateImageRequest {

    /**
     * 图片生成提示词
     */
    @NotBlank(message = "提示词不能为空")
    private String prompt;

    private String ration;
}
