package org.luckycloud.mapper.emoji;

import org.luckycloud.domain.emoji.CloudEmojiSyncTasksDO;

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

}
