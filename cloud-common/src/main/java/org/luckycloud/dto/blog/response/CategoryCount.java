package org.luckycloud.dto.blog.response;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
@Data
public class CategoryCount {

    private String categoryId;

    private String name;

    private Integer count;
}
