package org.luckycloud.mapper.user;

import org.luckycloud.domain.user.CloudRoleInfoDO;

/**
* @author lvyf
* @description 针对表【cloud_role_info】的数据库操作Mapper
* @createDate 2025-06-03 00:15:14
* @Entity org.luckycloud.domain.user.CloudRoleInfoDO
*/

public interface CloudRoleInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudRoleInfoDO record);

    int insertSelective(CloudRoleInfoDO record);

    CloudRoleInfoDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudRoleInfoDO record);

    int updateByPrimaryKey(CloudRoleInfoDO record);

}
