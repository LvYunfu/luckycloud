package org.luckycloud.mapper.user;

import org.apache.ibatis.annotations.Param;
import org.luckycloud.domain.user.CloudUserInfoDO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_user_info】的数据库操作Mapper
* @createDate 2025-06-03 00:15:14
* @Entity org.luckycloud.domain.user.CloudUserInfoDO
*/
public interface CloudUserInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudUserInfoDO record);

    int insertSelective(CloudUserInfoDO record);


    int updateByPrimaryKeySelective(CloudUserInfoDO record);


    CloudUserInfoDO findByUserId(String userId);

    CloudUserInfoDO findByUsername(String userName);

    CloudUserInfoDO findByMail(String mail);

    void updateByMailSelective(CloudUserInfoDO user);

    /**
     * 根据用户名或邮箱搜索用户
     */
    List<CloudUserInfoDO> searchUsers(@Param("keyword") String keyword);
}
