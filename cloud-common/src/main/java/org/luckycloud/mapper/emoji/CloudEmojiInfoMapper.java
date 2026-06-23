package org.luckycloud.mapper.emoji;

import org.luckycloud.domain.emoji.CloudEmojiInfoDO;

/**
* @author lvyf
* @description 针对表【cloud_emoji_info(表情包资产表)】的数据库操作Mapper
* @createDate 2026-06-24 00:06:31
* @Entity org.luckycloud.domain.emoji.CloudEmojiInfoDO
*/
public interface CloudEmojiInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudEmojiInfoDO record);

    int insertSelective(CloudEmojiInfoDO record);

    CloudEmojiInfoDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudEmojiInfoDO record);

    int updateByPrimaryKey(CloudEmojiInfoDO record);

}
