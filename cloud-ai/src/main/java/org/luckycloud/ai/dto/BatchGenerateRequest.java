package org.luckycloud.ai.dto;

import lombok.Data;

import java.util.List;

/**
 * 批量生成表情包请求
 */
@Data
public class BatchGenerateRequest {
    /**
     * 系列 ID
     */
    private String emojiGroupId;

    /**
     * 角色 IP ID
     */
    private String ipId;

    /**
     * 选中的描述列表对象数组
     */
    private List<DescriptionItem> descriptions;

    @Data
    public static class DescriptionItem {
        /**
         * 表情名称
         */
        private String name;

        /**
         * 前端拼接好的完整提示词（含情境/动作/元素）
         */
        private String promptText;
    }
}
