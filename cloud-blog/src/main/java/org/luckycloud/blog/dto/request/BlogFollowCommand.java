package org.luckycloud.blog.dto.request;

import lombok.Data;

import static org.luckycloud.constant.SystemConstant.ENABLE;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/20
 */
@Data
public class BlogFollowCommand {

    private String userId;

    private String followUserId;

    private String status=ENABLE;


}
