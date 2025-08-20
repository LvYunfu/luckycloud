package org.luckycloud.blog.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.luckycloud.dto.common.PageQuery;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommentQuery extends PageQuery {

    private String blogId;

    private String firstCommentId;


}
