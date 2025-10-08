package org.luckycloud.blog.service;

import org.luckycloud.blog.dto.request.WorkspaceCategoryCommand;
import org.luckycloud.blog.dto.request.WorkspaceToolCommand;
import org.luckycloud.blog.dto.response.WorkspaceResponse;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/9/25
 */
public interface WorkspaceService {
    void addCategory(WorkspaceCategoryCommand command);

    void addTool(WorkspaceToolCommand command);

    List<WorkspaceResponse> getTool();
}
