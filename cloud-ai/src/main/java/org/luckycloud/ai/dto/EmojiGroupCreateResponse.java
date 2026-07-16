package org.luckycloud.ai.dto;

import lombok.Data;

import java.util.List;

/**
 * 表情包系列创建响应
 *
 * @author lvyf
 * @date 2026/7/15
 */
@Data
public class EmojiGroupCreateResponse {
    /**
     * 系列ID
     */
    private String groupId;

    /**
     * 创建的表情包ID列表
     */
    private List<String> emojiIds;
}
