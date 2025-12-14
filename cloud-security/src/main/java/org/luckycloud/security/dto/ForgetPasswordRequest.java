package org.luckycloud.security.dto;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/3
 */
@Data
public class ForgetPasswordRequest {

    private String password;

    private String verifyCode;

    private String verifyId;

    private String mail;
}
