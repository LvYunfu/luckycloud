package org.luckycloud.ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 表情包信息更新命令
 */
@Data
public class EmojiInfoUpdateCommand {
    /**
     * 表情包唯一标识
     */
    @NotBlank(message = "表情包ID不能为空")
    private String emojiId;

    /**
     * 表情名称
     */
    private String name;

    /**
     * AI扩写后的最终图像提示词
     */
    private String promptText;

    /**
     * 文件 URL（PNG 或 GIF）
     */
    private String fileUrl;

    /**
     * 文件大小（KB）
     */
    private Integer fileSize;
}
