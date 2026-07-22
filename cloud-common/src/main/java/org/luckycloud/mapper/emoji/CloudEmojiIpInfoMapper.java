package org.luckycloud.mapper.emoji;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.emoji.CloudEmojiIpInfoDO;
import org.luckycloud.dto.emoji.request.EmojiIpListQueryDTO;
import org.luckycloud.dto.emoji.response.EmojiIpStatistic;

import java.util.List;

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

    /**
     * 根据条件查询角色IP列表
     * @param query 查询条件（包含用户ID、角色名称、来源类型、分页信息）
     * @return 角色IP列表
     */
    List<CloudEmojiIpInfoDO> selectIpList(@Param("query") EmojiIpListQueryDTO query);

    EmojiIpStatistic statisticIp(EmojiIpListQueryDTO queryDTO);
}
