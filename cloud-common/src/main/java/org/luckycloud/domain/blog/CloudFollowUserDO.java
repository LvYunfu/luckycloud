package org.luckycloud.domain.blog;

import lombok.Data;

/**
 * 关注信息表
 * @TableName cloud_follow_user
 */
@Data
public class CloudFollowUserDO {
    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String followUserId;

    /**
     * 
     */
    private String status;
}