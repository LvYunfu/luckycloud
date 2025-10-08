package org.luckycloud.blog.dto.request;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/9/25
 */
@Data
public class WorkspaceToolCommand {

    /**
     * 分类名称
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
