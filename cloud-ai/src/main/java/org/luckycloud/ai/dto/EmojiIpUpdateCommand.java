package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 表情IP更新命令
 */
@Data
public class EmojiIpUpdateCommand {
    /**
     * 主键
     */
    private String ipId;

    /**
     * ip标题
     */
    private String title;

    /**
     * IP描述
     */
    private String description;

    /**
     * ip文件路径
     */
    private String ipUrl;

    /**
     * 状态 1 有效 0 无效
     */
    private String status;
}
