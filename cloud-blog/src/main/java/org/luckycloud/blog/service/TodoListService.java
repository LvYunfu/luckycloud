package org.luckycloud.blog.service;

import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.TodoActivityLogResponse;
import org.luckycloud.blog.dto.response.TodoItemResponse;
import org.luckycloud.blog.dto.response.TodoListResponse;

import java.util.List;

/**
 * 待办清单服务接口
 * @author lvyf
 * @date 2026/4/11
 */
public interface TodoListService {

    /**
     * 创建待办清单
     */
    String createTodoList(TodoListCreateCommand command);

    /**
     * 更新待办清单
     */
    void updateTodoList(TodoListUpdateCommand command);

    /**
     * 删除待办清单
     */
    void deleteTodoList(String listId);

    /**
     * 获取用户的待办清单列表
     */
    List<TodoListResponse> getUserTodoLists();

    /**
     * 获取单个待办清单详情
     */
    TodoListResponse getTodoTask(TodoItemQuery query);

    /**
     * 创建任务项
     */
    String createTodoItem(TodoItemCreateCommand command);

    /**
     * 更新任务项
     */
    void updateTodoItem(TodoItemUpdateCommand command);

    /**
     * 删除任务项
     */
    void deleteTodoItem(String itemId);

    /**
     * 完成任务
     */
    void completeTodoItem(TodoItemCompleteCommand command);

    /**
     * 重新打开任务（将已完成的任务改为未完成）
     */
    void reopenTodoItem(String itemId);

    /**
     * 从未完成的任务中随机选择一个
     */
    TodoItemResponse getRandomUncompletedItem(TodoItemQuery query);

    List<TodoActivityLogResponse> getTodoListActivityLog(TodoItemQuery itemQuery);

    List<TodoItemResponse> getTodoItems(TodoItemQuery itemQuery);
}
