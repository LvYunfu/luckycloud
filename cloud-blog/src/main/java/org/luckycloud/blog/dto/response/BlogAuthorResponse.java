package org.luckycloud.blog.dto.response;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/11/20
 */
@Data
public class BlogAuthorResponse {

    private String userId;

    private String userName;

    private String avatar;

    private String profile;

    private int fanCount;

    private int blogCount;

    private int likeCount;

    private boolean followFlag;
}
