package org.luckycloud.mapper.todo;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.todo.CloudTodoItemsActivityLogsDO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_todo_items_activity_logs(执行记录表)】的数据库操作Mapper
* @createDate 2026-04-11 15:15:22
* @Entity org.luckycloud.domain.todo.CloudTodoItemsActivityLogsDO
*/
public interface CloudTodoItemsActivityLogsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudTodoItemsActivityLogsDO record);

    CloudTodoItemsActivityLogsDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudTodoItemsActivityLogsDO record);

    int updateByPrimaryKey(CloudTodoItemsActivityLogsDO record);

    /**
     * 根据任务ID查询活动记录列表
     */
    List<CloudTodoItemsActivityLogsDO> selectByItemId(@Param("itemId") String itemId);

}
