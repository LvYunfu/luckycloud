package org.luckycloud.dto.blog.request;

import lombok.Data;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/11/11
 */
@Data
public class BlogOperateQuery {

    private String blogId;

    private String userId;

    private List<String> operateType;
}
