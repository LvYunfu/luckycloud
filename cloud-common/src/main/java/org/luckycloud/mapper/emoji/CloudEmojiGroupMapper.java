package org.luckycloud.mapper.emoji;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.emoji.CloudEmojiGroupDO;
import org.luckycloud.dto.emoji.request.EmojiGroupQueryDTO;

import java.util.List;

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

    CloudEmojiGroupDO selectByPrimaryKey(String emojiGroupId);

    int updateByPrimaryKeySelective(CloudEmojiGroupDO record);

    int updateByPrimaryKey(CloudEmojiGroupDO record);

    /**
     * 根据条件查询表情包系列列表
     * @param query 查询条件
     * @return 系列列表
     */
    List<CloudEmojiGroupDO> selectGroupList(@Param("query") EmojiGroupQueryDTO query);

}
