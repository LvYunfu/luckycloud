package org.luckycloud.mapper.emoji;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.emoji.CloudEmojiSyncTasksDO;
import org.luckycloud.dto.emoji.request.SyncTaskQueryDTO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_emoji_sync_tasks(同步任务记录表)】的数据库操作Mapper
* @createDate 2026-06-24 00:06:31
* @Entity org.luckycloud.domain.emoji.CloudEmojiSyncTasksDO
*/
public interface CloudEmojiSyncTasksMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudEmojiSyncTasksDO record);

    int insertSelective(CloudEmojiSyncTasksDO record);

    CloudEmojiSyncTasksDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudEmojiSyncTasksDO record);

    int updateByPrimaryKey(CloudEmojiSyncTasksDO record);

    /**
     * 根据任务ID查询同步任务
     * @param taskId 任务ID
     * @return 同步任务
     */
    CloudEmojiSyncTasksDO selectByTaskId(@Param("taskId") String taskId);

    /**
     * 根据条件查询同步任务列表
     * @param query 查询条件
     * @return 同步任务列表
     */
    List<CloudEmojiSyncTasksDO> selectSyncTaskList(@Param("query") SyncTaskQueryDTO query);

}
