package org.luckycloud.blog.dto.request;

import lombok.Data;

/**
 * 创建待办清单请求
 * @author lvyf
 * @date 2026/4/11
 */
@Data
public class TodoListCreateCommand {

    /**
     * 清单标题
     */
    private String title;

    /**
     * 清单描述
     */
    private String description;
}
