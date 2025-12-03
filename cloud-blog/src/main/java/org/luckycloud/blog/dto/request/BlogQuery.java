package org.luckycloud.blog.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.luckycloud.dto.common.PageQuery;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlogQuery extends PageQuery {


    private String categoryId;

    private String tagName;

    private List<String> blogIdList;

    private String userId;
}
