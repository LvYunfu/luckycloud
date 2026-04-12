package org.luckycloud.mapper.todo;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.todo.CloudTodoListsDO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_todo_lists(待办清单表)】的数据库操作Mapper
* @createDate 2026-04-11 15:15:22
* @Entity org.luckycloud.domain.todo.CloudTodoListsDO
*/
public interface CloudTodoListsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudTodoListsDO record);

    CloudTodoListsDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudTodoListsDO record);

    int updateByPrimaryKey(CloudTodoListsDO record);

    /**
     * 查询用户的待办清单列表
     */
    List<CloudTodoListsDO> selectByUserId(@Param("userId") String userId);

    /**
     * 根据清单ID查询
     */
    CloudTodoListsDO selectByListId(@Param("listId") String listId);

}
