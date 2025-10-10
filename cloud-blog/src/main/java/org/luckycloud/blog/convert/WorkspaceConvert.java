package org.luckycloud.blog.convert;

import org.luckycloud.blog.dto.request.WorkspaceCategoryCommand;
import org.luckycloud.blog.dto.request.WorkspaceToolCommand;
import org.luckycloud.blog.dto.response.WorkspaceResponse;
import org.luckycloud.blog.dto.response.WorkspaceToolResponse;
import org.luckycloud.domain.workspace.CloudWorkspaceCategoryInfoDO;
import org.luckycloud.domain.workspace.CloudWorkspaceToolInfoDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.luckycloud.constant.SystemConstant.ENABLE;

/**
 * @author lvyf
 * @description:
 * @date 2025/10/8
 */
@Mapper(componentModel = "spring")

public interface WorkspaceConvert {

    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "userId", expression = "java(org.luckycloud.security.util.UserUtils.getUserId())")
    CloudWorkspaceCategoryInfoDO toCategoryDO(WorkspaceCategoryCommand command);

    @Mapping(target = "createTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "userId", expression = "java(org.luckycloud.security.util.UserUtils.getUserId())")
    CloudWorkspaceToolInfoDO toCategoryDO(WorkspaceToolCommand command);

    WorkspaceResponse toWorkspaceResponse(CloudWorkspaceCategoryInfoDO categoryInfoDO);

    List<WorkspaceResponse> toWorkspaceResponse(List<CloudWorkspaceCategoryInfoDO> list);

    WorkspaceToolResponse toToolResponse(CloudWorkspaceToolInfoDO toolInfoDO);

    List<WorkspaceToolResponse> toToolResponse(List<CloudWorkspaceToolInfoDO> list);
}
