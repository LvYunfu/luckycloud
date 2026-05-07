package org.luckycloud.mapper.emoji;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.emoji.CloudEmojiGroupDO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_emoji_group(表情包系列)】的数据库操作Mapper
* @createDate 2026-05-06 23:51:53
* @Entity org.luckycloud.domain.emoji.CloudEmojiGroupDO
*/
public interface CloudEmojiGroupMapper {

    int deleteByPrimaryKey(String emojiGroupId);

    int insert(CloudEmojiGroupDO record);


    CloudEmojiGroupDO selectByPrimaryKey(String emojiGroupId);

    int updateByPrimaryKeySelective(CloudEmojiGroupDO record);

    int updateByPrimaryKey(CloudEmojiGroupDO record);

    /**
     * 查询所有表情包系列
     */
    List<CloudEmojiGroupDO> selectAll();


}
