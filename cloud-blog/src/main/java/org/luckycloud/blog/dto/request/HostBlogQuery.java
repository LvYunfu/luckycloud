package org.luckycloud.blog.dto.request;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/11/24
 */
@Data
public class HostBlogQuery {

    private int number = 5;

    private String categoryId;
}
