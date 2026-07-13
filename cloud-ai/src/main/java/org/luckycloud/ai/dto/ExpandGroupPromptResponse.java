package org.luckycloud.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 表情包系列提示词扩写响应
 *
 * @author lvyf
 * @date 2026/7/9
 */
@Data
public class ExpandGroupPromptResponse {

    /**
     * 扩写后的提示词列表
     */
    private List<PromptItem> items;

    /**
     * 单个提示词项
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromptItem {

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
    }
}
