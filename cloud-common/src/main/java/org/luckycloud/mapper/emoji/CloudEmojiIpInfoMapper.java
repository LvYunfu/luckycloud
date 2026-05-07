package org.luckycloud.mapper.emoji;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.emoji.CloudEmojiIpInfoDO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_emoji_ip_info(表情主题ip)】的数据库操作Mapper
* @createDate 2026-05-06 23:51:53
* @Entity org.luckycloud.domain.emoji.CloudEmojiIpInfoDO
*/
public interface CloudEmojiIpInfoMapper {

    int deleteByPrimaryKey(String ipId);

    int insert(CloudEmojiIpInfoDO record);

    int insertSelective(CloudEmojiIpInfoDO record);

    CloudEmojiIpInfoDO selectByPrimaryKey(String ipId);

    int updateByPrimaryKeySelective(CloudEmojiIpInfoDO record);

    int updateByPrimaryKey(CloudEmojiIpInfoDO record);

    /**
     * 查询所有表情IP
     */
    List<CloudEmojiIpInfoDO> selectAll();

    /**
     * 根据条件查询表情IP列表
     */
    List<CloudEmojiIpInfoDO> selectByCondition(@Param("status") String status);

}
