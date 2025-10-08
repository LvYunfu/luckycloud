package org.luckycloud.domain.workspace;

import java.util.Date;
import lombok.Data;

/**
 *
 * @TableName cloud_workspace_category_info
 */
@Data
public class CloudWorkspaceCategoryInfoDO {
    /**
     * 分类ID
     */
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

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
