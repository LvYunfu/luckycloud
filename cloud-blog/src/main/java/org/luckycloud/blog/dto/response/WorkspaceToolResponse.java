package org.luckycloud.blog.dto.response;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/10/8
 */
@Data
public class WorkspaceToolResponse {
    /**
     * 工具ID
     */
    private String toolId;

    /**
     * 所属分类
     */
    private String categoryId;

    /**
     * 工具标题
     */
    private String toolTitle;

    /**
     * 工具图标
     */
    private String toolIcon;

    /**
     * 工具URL
     */
    private String toolUrl;
}
