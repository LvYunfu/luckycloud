package org.luckycloud.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 表情包系列提示词扩写请求
 *
 * @author lvyf
 * @date 2026/7/9
 */
@Data
public class ExpandGroupPromptRequest {

    /**
     * IP描述（≤200字符）
     */
    @NotBlank(message = "IP描述不能为空")
    @Size(max = 200, message = "IP描述不能超过200字符")
    private String ipDescription;

    /**
     * 表情包系列关键词（≤100字符）
     */
    @NotBlank(message = "系列关键词不能为空")
    @Size(max = 100, message = "系列关键词不能超过100字符")
    private String keywords;

    /**
     * 生成提示词数量
     */
    @NotNull(message = "生成数量不能为空")
    private Integer quantity;

    /**
     * 绘画风格
     */
    private String style;
}
