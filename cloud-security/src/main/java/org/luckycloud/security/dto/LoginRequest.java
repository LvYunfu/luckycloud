package org.luckycloud.security.dto;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2024/7/3
 */
@Data
public class LoginRequest {

    private String userName;


    private String password;
}

