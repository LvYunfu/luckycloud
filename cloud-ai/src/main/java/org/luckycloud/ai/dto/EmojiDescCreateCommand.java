package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 表情IP创建命令
 */
@Data
public class EmojiDescCreateCommand {
    /**
     * ip标题
     */
    private String title;

    /**
     * IP描述
     */
    private String description;

}
