package org.luckycloud.blog.dto.request;

import lombok.Data;

/**
 * 用户搜索请求
 * @author lvyf
 * @date 2026/4/22
 */
@Data
public class UserSearchCommand {

    /**
     * 搜索关键词(用户名或邮箱)
     */
    private String keyword;
}
