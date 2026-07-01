package org.luckycloud.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 表情IP创建命令
 */
@Data
public class EmojiIpCreateCommand {
    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 255, message = "角色名称不能超过255个字符")
    private String name;

    /**
     * 角色图片 URL
     */
    @NotBlank(message = "角色图片URL不能为空")
    @Size(max = 500, message = "图片URL不能超过500个字符")
    private String imageUrl;

    /**
     * 来源类型：ai_generated / user_upload
     */
    @NotBlank(message = "来源类型不能为空")
    private String sourceType;

    /**
     * 用户输入的角色关键词（AI生成时必填）
     */
    @Size(max = 255, message = "角色关键词不能超过255个字符")
    private String sourceKeyword;

    /**
     * AI扩写后的提示词（AI生成时必填）
     */
    private String promptText;

    /**
     * 角色描述（≤1000字符）
     */
    @Size(max = 1000, message = "角色描述不能超过1000个字符")
    private String description;

    /**
     * 是否默认角色：0-否，1-是，默认0
     */
    @Range(min = 0, max = 1, message = "是否默认角色只能为0或1")
    private Integer isDefault;
}
