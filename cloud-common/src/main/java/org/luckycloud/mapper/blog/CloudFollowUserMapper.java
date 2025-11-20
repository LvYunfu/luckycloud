package org.luckycloud.mapper.blog;

import org.luckycloud.domain.blog.CloudFollowUserDO;

/**
* @author lvyf
* @description 针对表【cloud_follow_user(关注信息表)】的数据库操作Mapper
* @createDate 2025-11-20 23:21:58
* @Entity org.luckycloud.domain.blog.CloudFollowUserDO
*/
public interface CloudFollowUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudFollowUserDO record);

    int insertSelective(CloudFollowUserDO record);

    CloudFollowUserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudFollowUserDO record);

    int updateByPrimaryKey(CloudFollowUserDO record);

    int countFollowFans(String userId);

    int checkFollowUser(String userId, String followUserId);
}
