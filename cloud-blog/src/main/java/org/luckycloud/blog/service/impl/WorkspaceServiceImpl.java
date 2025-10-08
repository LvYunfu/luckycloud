package org.luckycloud.blog.service.impl;

import jakarta.annotation.Resource;
import org.luckycloud.blog.convert.WorkspaceConvert;
import org.luckycloud.blog.dto.request.WorkspaceCategoryCommand;
import org.luckycloud.blog.dto.request.WorkspaceToolCommand;
import org.luckycloud.blog.dto.response.WorkspaceResponse;
import org.luckycloud.blog.service.WorkspaceService;
import org.luckycloud.domain.workspace.CloudWorkspaceCategoryInfoDO;
import org.luckycloud.domain.workspace.CloudWorkspaceToolInfoDO;
import org.luckycloud.mapper.workspace.CloudWorkspaceCategoryInfoMapper;
import org.luckycloud.mapper.workspace.CloudWorkspaceToolInfoMapper;
import org.luckycloud.security.util.UserUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lvyf
 * @description:
 * @date 2025/9/25
 */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Resource
    private CloudWorkspaceCategoryInfoMapper categoryInfoMapper;

    @Resource
    private CloudWorkspaceToolInfoMapper toolInfoMapper;

    @Resource
    private WorkspaceConvert workspaceConvert;

    @Override
    public void addCategory(WorkspaceCategoryCommand command) {
        CloudWorkspaceCategoryInfoDO categoryInfo = workspaceConvert.toCategoryDO(command);
        categoryInfoMapper.insert(categoryInfo);
    }

    @Override
    public void addTool(WorkspaceToolCommand command) {
        CloudWorkspaceToolInfoDO toolInfo = workspaceConvert.toCategoryDO(command);
        toolInfoMapper.insert(toolInfo);
    }

    @Override
    public List<WorkspaceResponse> getTool() {
        String userId = UserUtils.getUserId();
        List<CloudWorkspaceCategoryInfoDO> list = categoryInfoMapper.selectUserCategories(userId);
        List<String> categoryIds = list.stream().map(CloudWorkspaceCategoryInfoDO::getCategoryId).toList();
        if (categoryIds.isEmpty()) {
            return List.of();
        }
        List<CloudWorkspaceToolInfoDO> toolList = toolInfoMapper.selectByCategoryIds(categoryIds);
        Map<String, List<CloudWorkspaceToolInfoDO>> toolMap = toolList.stream().collect(Collectors.groupingBy(CloudWorkspaceToolInfoDO::getCategoryId));
        List<WorkspaceResponse> responseList = workspaceConvert.toWorkspaceResponse(list);
        responseList.forEach(item -> item.setTools(workspaceConvert.toToolResponse(toolMap.get(item.getCategoryId()))));
        return responseList;
    }


}
