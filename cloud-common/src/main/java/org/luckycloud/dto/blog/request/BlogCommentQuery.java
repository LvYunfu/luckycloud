package org.luckycloud.dto.blog.request;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/20
 */
@Data
public class BlogCommentQuery {

    private String blogId;


    private String firstCommentId;

    private String sortOrder;
}
