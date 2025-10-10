package org.luckycloud.domain.workspace;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 *
 * @TableName cloud_workspace_tool_info
 */
@Data
public class CloudWorkspaceToolInfoDO {
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

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
