package org.luckycloud.mapper.workspace;

import org.luckycloud.domain.workspace.CloudWorkspaceToolInfoDO;

import java.util.List;

/**
* @author lvyf
* @description 针对表【cloud_workspace_tool_info】的数据库操作Mapper
* @createDate 2025-09-25 00:12:37
* @Entity org.luckycloud.domain.workspace.CloudWorkspaceToolInfoDO
*/
public interface CloudWorkspaceToolInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CloudWorkspaceToolInfoDO record);

    int insertSelective(CloudWorkspaceToolInfoDO record);

    CloudWorkspaceToolInfoDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudWorkspaceToolInfoDO record);

    int updateByPrimaryKey(CloudWorkspaceToolInfoDO record);

    List<CloudWorkspaceToolInfoDO> selectByCategoryIds(List<String> categoryIds);
}
