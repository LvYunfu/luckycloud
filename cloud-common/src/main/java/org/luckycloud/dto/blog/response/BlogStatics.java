package org.luckycloud.dto.blog.response;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/9/1
 */
@Data
public class BlogStatics {

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 浏览数
     */
    private Integer viewCount;

    /**
     * 收藏数
     */
    private Integer collectCount;


}
