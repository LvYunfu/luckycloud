package org.luckycloud.blog.dto.response;

import lombok.Data;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/10/8
 */
@Data
public class WorkspaceResponse {

    private String categoryId;
    /**
     * 分类名称
     */

    private String categoryTitle;

    /**
     * 分类图标
     */
    private String categoryIcon;

    /**
     * 分类图标背景
     */
    private String categoryIconBackground;

    private List<WorkspaceToolResponse> tools;
}
