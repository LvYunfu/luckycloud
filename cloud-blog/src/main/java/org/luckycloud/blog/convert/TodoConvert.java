package org.luckycloud.blog.convert;

import org.luckycloud.blog.dto.request.*;
import org.luckycloud.blog.dto.response.TodoActivityLogResponse;
import org.luckycloud.blog.dto.response.TodoItemResponse;
import org.luckycloud.blog.dto.response.TodoListResponse;
import org.luckycloud.domain.todo.CloudTodoItemsActivityLogsDO;
import org.luckycloud.domain.todo.CloudTodoItemsDO;
import org.luckycloud.domain.todo.CloudTodoListsDO;
import org.luckycloud.dto.todo.ItemQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

import static org.luckycloud.constant.SystemConstant.ENABLE;

/**
 * 待办清单对象转换器
 * @author lvyf
 * @date 2026/4/12
 */
@Mapper(componentModel = "spring")
public interface TodoConvert {

    TodoConvert INSTANCE = Mappers.getMapper(TodoConvert.class);

    /**
     * DO转Response - 清单
     */
    TodoListResponse convert2TodoListResponse(CloudTodoListsDO todoListsDO);

    List<TodoListResponse> convert2TodoListResponse(List<CloudTodoListsDO> todoListsDO);

    /**
     * Command转DO - 创建清单
     */
    @Mapping(target = "listId", ignore = true)
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", constant = ENABLE)
    @Mapping(target = "userId", expression = "java(org.luckycloud.security.util.UserUtils.getUserId())")
    CloudTodoListsDO convert2DO(TodoListCreateCommand command);

    /**
     * Command转DO - 更新清单
     */
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    CloudTodoListsDO convert2DO(TodoListUpdateCommand command);

    /**
     * Command转DO - 创建任务项
     */
    @Mapping(target = "itemId", ignore = true)
    @Mapping(target = "itemStatus", constant = "IS00")
    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "completedTime", ignore = true)
    @Mapping(target = "status", constant = ENABLE)
    CloudTodoItemsDO convert2DO(TodoItemCreateCommand command);

    /**
     * Command转DO - 更新任务项
     */
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    CloudTodoItemsDO convert2DO(TodoItemUpdateCommand command);

    /**
     * DO转Response - 任务项
     */
    TodoItemResponse convert2TodoItemResponse(CloudTodoItemsDO item);

    List<TodoItemResponse> convert2TodoItemResponse(List<CloudTodoItemsDO> items);

    /**
     * DO转Response - 活动记录
     */
    @Mapping(target = "imageUrls",expression = "java(org.luckycloud.utils.JsonUtils.parseList(log.getImageUrls(),new com.fasterxml.jackson.core.type.TypeReference<List<String>>(){}))")
    TodoActivityLogResponse convert2ActivityLogResponse(CloudTodoItemsActivityLogsDO log);


    List<TodoActivityLogResponse> convert2ActivityLogResponse(List<CloudTodoItemsActivityLogsDO> logs);

    ItemQuery convert2Query(TodoItemQuery query);
}
