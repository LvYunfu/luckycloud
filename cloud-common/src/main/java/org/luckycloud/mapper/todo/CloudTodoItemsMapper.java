package org.luckycloud.mapper.todo;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.todo.CloudTodoItemsDO;
import org.luckycloud.domain.todo.TodoItemStatistics;
import org.luckycloud.dto.todo.ItemQuery;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_todo_items(任务项表)】的数据库操作Mapper
* @createDate 2026-04-11 15:15:22
* @Entity org.luckycloud.domain.todo.CloudTodoItemsDO
*/
public interface CloudTodoItemsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudTodoItemsDO record);


    CloudTodoItemsDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudTodoItemsDO record);

    int updateByPrimaryKey(CloudTodoItemsDO record);

    /**
     * 根据清单ID查询任务列表
     */
    List<CloudTodoItemsDO> selectByList(@Param("query") ItemQuery query);

    /**
     * 根据任务ID查询
     */
    CloudTodoItemsDO selectByItemId(@Param("itemId") String itemId);

    /**
     * 查询未完成的任务列表
     */
    List<CloudTodoItemsDO> selectUncompletedByListId(@Param("listId") String listId);

    /**
     * 统计清单中的任务总数和已完成数
     */
    List<TodoItemStatistics> countTodoItems(@Param("listIds") List<String> listIds);

}
