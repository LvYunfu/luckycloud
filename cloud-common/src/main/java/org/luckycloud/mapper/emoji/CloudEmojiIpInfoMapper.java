package org.luckycloud.mapper.emoji;

import org.luckycloud.domain.emoji.CloudEmojiIpInfoDO;

/**
* @author lvyf
* @description 针对表【cloud_emoji_ip_info(表情主题 IP（角色）表)】的数据库操作Mapper
* @createDate 2026-06-24 00:06:31
* @Entity org.luckycloud.domain.emoji.CloudEmojiIpInfoDO
*/
public interface CloudEmojiIpInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudEmojiIpInfoDO record);

    int insertSelective(CloudEmojiIpInfoDO record);

    CloudEmojiIpInfoDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudEmojiIpInfoDO record);

    int updateByPrimaryKey(CloudEmojiIpInfoDO record);

}
