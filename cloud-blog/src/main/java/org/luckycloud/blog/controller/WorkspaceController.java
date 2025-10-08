package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.dto.request.WorkspaceCategoryCommand;
import org.luckycloud.blog.dto.request.WorkspaceToolCommand;
import org.luckycloud.blog.dto.response.WorkspaceResponse;
import org.luckycloud.blog.service.WorkspaceService;
import org.luckycloud.dto.common.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/9/25
 */
@RestController
@RequestMapping("/workspace")
public class WorkspaceController {


    @Resource
    private WorkspaceService workspaceService;

    @PostMapping("/add-category")
    public Response<Void> addCategory(@RequestBody WorkspaceCategoryCommand command) {
        workspaceService.addCategory(command);
        return Response.success("分类添加成功");
    }


    @PostMapping("/add-tool")
    public Response<Void> addTool(@RequestBody WorkspaceToolCommand command) {
        workspaceService.addTool(command);
        return Response.success("工具添加成功");
    }

    @PostMapping("/get-tool")
    public List<WorkspaceResponse> getTool() {
        return  workspaceService.getTool();
    }
}
