package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 批量生成表情包命令（单个表情包项）
 *
 * @author lvyf
 * @date 2026/7/16
 */
@Data
public class BatchGenerateCommand {

    /**
     * 标题
     */
    private String title;

    /**
     * 情景描述
     */
    private String scenario;

    /**
     * 元素（逗号分隔的关键元素）
     */
    private String elements;

    /**
     * 具体的绘图提示词
     */
    private String promptText;

    /**
     * 系列 ID
     */
    private String emojiGroupId;

    /**
     * 角色 IP ID
     */
    private String ipId;

    /**
     * 表情包 ID（用于更新已创建的表情包记录）
     */
    private String emojiId;
}
