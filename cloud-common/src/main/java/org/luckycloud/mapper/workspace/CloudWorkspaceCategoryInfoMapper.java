package org.luckycloud.mapper.workspace;

import org.luckycloud.domain.workspace.CloudWorkspaceCategoryInfoDO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_workspace_category_info】的数据库操作Mapper
* @createDate 2025-09-25 00:09:00
* @Entity org.luckycloud.domain.workspace.CloudWorkspaceCategoryInfoDO
*/
public interface CloudWorkspaceCategoryInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudWorkspaceCategoryInfoDO record);

    int insertSelective(CloudWorkspaceCategoryInfoDO record);

    CloudWorkspaceCategoryInfoDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudWorkspaceCategoryInfoDO record);

    int updateByPrimaryKey(CloudWorkspaceCategoryInfoDO record);

    List<CloudWorkspaceCategoryInfoDO> selectUserCategories(String userId);
}
