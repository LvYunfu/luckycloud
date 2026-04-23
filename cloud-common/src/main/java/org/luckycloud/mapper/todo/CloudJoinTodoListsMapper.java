package org.luckycloud.mapper.todo;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.todo.CloudJoinTodoListsDO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_join_todo_lists】的数据库操作Mapper
* @createDate 2026-04-22 22:32:23
* @Entity org.luckycloud.domain.todo.CloudJoinTodoListsDO
*/
public interface CloudJoinTodoListsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudJoinTodoListsDO record);

    int insertSelective(CloudJoinTodoListsDO record);

    CloudJoinTodoListsDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudJoinTodoListsDO record);

    int updateByPrimaryKey(CloudJoinTodoListsDO record);

    /**
     * 根据清单ID查询参与者列表
     */
    List<CloudJoinTodoListsDO> selectByListId(@Param("listId") String listId);

    /**
     * 根据用户ID查询参与的清单列表
     */
    List<CloudJoinTodoListsDO> selectByUserId(@Param("userId") String userId);

    /**
     * 根据清单ID和用户ID查询参与记录
     */
    CloudJoinTodoListsDO selectByListIdAndUserId(@Param("listId") String listId, @Param("userId") String userId);

    void deleteUserJoin(CloudJoinTodoListsDO joinRecord);
}
