package org.luckycloud.mapper.emoji;

import org.luckycloud.domain.emoji.CloudEmojiGroupDO;

/**
* @author lvyf
* @description 针对表【cloud_emoji_group(表情包系列表)】的数据库操作Mapper
* @createDate 2026-06-24 00:06:31
* @Entity org.luckycloud.domain.emoji.CloudEmojiGroupDO
*/
public interface CloudEmojiGroupMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudEmojiGroupDO record);

    int insertSelective(CloudEmojiGroupDO record);

    CloudEmojiGroupDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudEmojiGroupDO record);

    int updateByPrimaryKey(CloudEmojiGroupDO record);

}
