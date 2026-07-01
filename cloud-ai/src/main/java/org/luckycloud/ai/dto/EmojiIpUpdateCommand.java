package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 更新角色信息请求
 */
@Data
public class EmojiIpUpdateCommand {
    /**
     * 角色 ID
     */
    private String ipId;

    /**
     * 新的角色名称
     */
    private String name;

    /**
     * 新的角色描述
     */
    private String description;

    /**
     * 是否设为默认：0-否，1-是
     */
    private Integer isDefault;
}
