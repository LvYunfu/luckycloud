package org.luckycloud.dto.blog.request;

import lombok.Data;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
@Data
public class BlogQuery {

    private String content;

    private String categoryId;


    private String blogStatus;

    private List<String> blogIdList;
}
