package org.luckycloud.blog.dto.request;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2026/4/12
 */
@Data
public class TodoItemQuery {

    private String listId;

    private String itemId;

    private String itemsStatus;

}
