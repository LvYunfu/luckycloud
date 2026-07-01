package org.luckycloud.mapper.emoji;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.emoji.CloudEmojiInfoDO;
import org.luckycloud.dto.emoji.request.EmojiInfoQueryDTO;

import java.util.List;

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

    CloudEmojiInfoDO selectByPrimaryKey(String emojiId);

    int updateByPrimaryKeySelective(CloudEmojiInfoDO record);

    int updateByPrimaryKey(CloudEmojiInfoDO record);

    /**
     * 根据系列ID统计表情包数量
     * @param emojiGroupId 系列ID
     * @return 表情包数量
     */
    int countByGroupId(@Param("emojiGroupId") String emojiGroupId);

    /**
     * 根据系列ID批量逻辑删除表情包
     * @param emojiGroupId 系列ID
     * @return 影响行数
     */
    int deleteByGroupId(@Param("emojiGroupId") String emojiGroupId);

    /**
     * 根据条件查询表情包列表
     * @param query 查询条件
     * @return 表情包列表
     */
    List<CloudEmojiInfoDO> selectEmojiInfoList(@Param("query") EmojiInfoQueryDTO query);

}
