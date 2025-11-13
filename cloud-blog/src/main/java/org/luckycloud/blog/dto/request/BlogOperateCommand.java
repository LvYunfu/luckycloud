package org.luckycloud.blog.dto.request;

import lombok.Data;

import static org.luckycloud.constant.SystemConstant.ENABLE;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/20
 */
@Data
public class BlogOperateCommand {

    private String blogId;

    private String commentId;

    private String status=ENABLE;


}
