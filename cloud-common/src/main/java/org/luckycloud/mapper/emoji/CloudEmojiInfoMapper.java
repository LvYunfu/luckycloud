package org.luckycloud.mapper.emoji;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.emoji.CloudEmojiInfoDO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_emoji_info(表情包信息表)】的数据库操作Mapper
* @createDate 2026-05-06 23:51:53
* @Entity org.luckycloud.domain.emoji.CloudEmojiInfoDO
*/
public interface CloudEmojiInfoMapper {

    int deleteByPrimaryKey(String emojiId);

    int insert(CloudEmojiInfoDO record);

    int insertSelective(CloudEmojiInfoDO record);

    CloudEmojiInfoDO selectByPrimaryKey(String emojiId);

    int updateByPrimaryKeySelective(CloudEmojiInfoDO record);

    int updateByPrimaryKey(CloudEmojiInfoDO record);

    /**
     * 批量插入表情包
     */
    int batchInsert(@Param("list") List<CloudEmojiInfoDO> list);

    /**
     * 查询所有表情包
     */
    List<CloudEmojiInfoDO> selectAll();

    /**
     * 根据条件查询表情包列表
     */
    List<CloudEmojiInfoDO> selectByCondition(@Param("status") String status, @Param("emojiGroupId") String emojiGroupId);

}
