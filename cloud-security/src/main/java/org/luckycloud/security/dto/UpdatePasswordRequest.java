package org.luckycloud.security.dto;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/3
 */
@Data
public class UpdatePasswordRequest {

    private String oldPassword;

    private String newPassword;


    private String userId;
}
