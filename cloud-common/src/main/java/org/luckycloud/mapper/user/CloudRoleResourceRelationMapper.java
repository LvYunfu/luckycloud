package org.luckycloud.mapper.user;

import org.luckycloud.domain.user.CloudRoleResourceRelationDO;

/**
* @author lvyf
* @description 针对表【cloud_role_resource_relation】的数据库操作Mapper
* @createDate 2025-06-03 00:15:14
* @Entity org.luckycloud.domain.user.CloudRoleResourceRelationDO
*/

public interface CloudRoleResourceRelationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudRoleResourceRelationDO record);

    int insertSelective(CloudRoleResourceRelationDO record);

    CloudRoleResourceRelationDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudRoleResourceRelationDO record);

    int updateByPrimaryKey(CloudRoleResourceRelationDO record);

}
