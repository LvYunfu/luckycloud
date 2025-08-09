package org.luckycloud.blog.dto.response;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
@Data
public class BlogCategoryCountResponse {

    private String categoryId;

    private String name;

    private Integer count;
}
