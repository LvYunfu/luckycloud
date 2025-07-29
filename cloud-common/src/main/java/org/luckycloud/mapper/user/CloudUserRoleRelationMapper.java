package org.luckycloud.mapper.user;

import org.luckycloud.domain.user.CloudUserRoleRelationDO;

/**
* @author lvyf
* @description 针对表【cloud_user_role_relation】的数据库操作Mapper
* @createDate 2025-06-03 00:15:14
* @Entity org.luckycloud.domain.user.CloudUserRoleRelationDO
*/
public interface CloudUserRoleRelationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudUserRoleRelationDO record);

    int insertSelective(CloudUserRoleRelationDO record);

    CloudUserRoleRelationDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudUserRoleRelationDO record);

    int updateByPrimaryKey(CloudUserRoleRelationDO record);

}
