package org.luckycloud.blog.dto.request;

import lombok.Data;
import org.luckycloud.dto.common.PageQuery;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/20
 */
@Data
public class CommentQuery extends PageQuery {

    private String blogId;

    private String commentId;

}
