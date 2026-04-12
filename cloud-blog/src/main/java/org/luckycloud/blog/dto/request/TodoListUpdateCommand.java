package org.luckycloud.blog.dto.request;

import lombok.Data;

/**
 * 更新待办清单请求
 * @author lvyf
 * @date 2026/4/11
 */
@Data
public class TodoListUpdateCommand {

    /**
     * 清单ID
     */
    private String listId;

    /**
     * 清单标题
     */
    private String title;

    /**
     * 清单描述
     */
    private String description;

    /**
     * 是否启用：1-是，0-否
     */
    private String status;
}
