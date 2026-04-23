package org.luckycloud.blog.dto.response;

import lombok.Data;

/**
 * 用户搜索响应
 * @author lvyf
 * @date 2026/4/22
 */
@Data
public class UserSearchResponse {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个人简介
     */
    private String profile;
}
