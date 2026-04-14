package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.TodoActivityLogResponse;
import org.luckycloud.blog.dto.response.TodoItemResponse;
import org.luckycloud.blog.dto.response.TodoListResponse;
import org.luckycloud.blog.service.TodoListService;
import org.luckycloud.dto.common.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待办清单控制器
 * @author lvyf
 * @date 2026/4/11
 */
@RestController
@RequestMapping("/todo-list")
public class TodoListController {

    @Resource
    private TodoListService todoListService;

    /**
     * 创建待办清单
     */
    @PostMapping("/create")
    public Response<String> createTodoList(@RequestBody TodoListCreateCommand command) {
        String listId = todoListService.createTodoList(command);
        return Response.successData(listId, "待办清单创建成功");
    }

    /**
     * 更新待办清单
     */
    @PostMapping("/update")
    public Response<Void> updateTodoList(@RequestBody TodoListUpdateCommand command) {
        todoListService.updateTodoList(command);
        return Response.success("待办清单更新成功");
    }

    /**
     * 删除待办清单
     */
    @PostMapping("/delete")
    public Response<Void> deleteTodoList(@RequestParam String listId) {
        todoListService.deleteTodoList(listId);
        return Response.success("待办清单删除成功");
    }

    /**
     * 获取用户的待办清单列表
     */
    @GetMapping("/get-user-todo")
    public Response<List<TodoListResponse>> getUserTodoLists() {
        List<TodoListResponse> lists = todoListService.getUserTodoLists();
        return Response.successData(lists);
    }

    /**
     * 获取单个待办清单详情
     */
    @PostMapping("/get-todo")
    public Response<TodoListResponse> getTodoTask(@RequestBody TodoItemQuery itemQuery) {
        TodoListResponse detail = todoListService.getTodoTask(itemQuery);
        return Response.successData(detail);
    }

    /**
     * 获取单个待办清单详情
     */
    @PostMapping("/get-todo-items")
    public Response<List<TodoItemResponse>> getTodoItems(@RequestBody TodoItemQuery itemQuery) {
        List<TodoItemResponse> list = todoListService.getTodoItems(itemQuery);
        return Response.successData(list);
    }
    /**
     * 获取待办清单的动态日志
     * @param itemQuery
     * @return
     */
    @PostMapping("/get-activity-log")
    public Response<List<TodoActivityLogResponse>> getTodoListActivityLog(@RequestBody TodoItemQuery itemQuery) {
        List<TodoActivityLogResponse> list = todoListService.getTodoListActivityLog(itemQuery);
        return Response.successData(list);
    }


    /**
     * 创建任务项
     */
    @PostMapping("/create-item")
    public Response<String> createTodoItem(@RequestBody TodoItemCreateCommand command) {
        String itemId = todoListService.createTodoItem(command);
        return Response.successData(itemId, "任务项创建成功");
    }

    /**
     * 更新任务项
     */
    @PostMapping("/update-item")
    public Response<Void> updateTodoItem(@RequestBody TodoItemUpdateCommand command) {
        todoListService.updateTodoItem(command);
        return Response.success("任务项更新成功");
    }

    /**
     * 删除任务项
     */
    @PostMapping("/delete-item")
    public Response<Void> deleteTodoItem(@RequestParam String itemId) {
        todoListService.deleteTodoItem(itemId);
        return Response.success("任务项删除成功");
    }

    /**
     * 完成任务
     */
    @PostMapping("/complete-item")
    public Response<Void> completeTodoItem(@RequestBody TodoItemCompleteCommand command) {
        todoListService.completeTodoItem(command);
        return Response.success("任务完成，恭喜您！");
    }

    /**
     * 重新打开任务（将已完成的任务改为未完成）
     */
    @PostMapping("/reopen-item")
    public Response<Void> reopenTodoItem(@RequestParam String itemId) {
        todoListService.reopenTodoItem(itemId);
        return Response.success("任务已重新打开");
    }

    /**
     * 从未完成的任务中随机选择一个
     */
    @PostMapping("/random-uncompleted")
    public Response<TodoItemResponse> getRandomUncompletedItem(@RequestBody TodoItemQuery query) {
        TodoItemResponse item = todoListService.getRandomUncompletedItem(query);
        return Response.successData(item);
    }
}
