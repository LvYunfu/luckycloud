package org.luckycloud.blog.dto.request;

import lombok.Data;

/**
 * 更新任务项请求
 * @author lvyf
 * @date 2026/4/11
 */
@Data
public class TodoItemUpdateCommand {

    /**
     * 任务ID
     */
    private String itemId;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务详细描述
     */
    private String description;

    /**
     * 排序权重
     */
    private Integer sortOrder;
}
