package org.luckycloud.security.dto;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/2
 */
@Data
public class UserInfoResponse {

    private String userId;

    private String userName;

    private String mail;

    private String profile;

    private String avatar;


}
