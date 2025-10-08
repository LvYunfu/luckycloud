package org.luckycloud.blog.dto.request;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/9/25
 */
@Data
public class WorkspaceCategoryCommand {

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
}
