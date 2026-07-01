package org.luckycloud.ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 表情包系列更新命令
 */
@Data
public class EmojiGroupUpdateCommand {
    /**
     * 系列唯一标识
     */
    @NotBlank(message = "系列ID不能为空")
    private String emojiGroupId;

    /**
     * 关联的角色 IP ID
     */
    private String ipId;

    /**
     * 用户输入的系列关键词（逗号分隔）
     */
    private String seriesKeywords;

    /**
     * 生成类型：static(静态) / dynamic(动态)
     */
    private String emojiType;

    /**
     * 表情风格（如"可爱"、"写实"）
     */
    private String emojiStyle;
}
