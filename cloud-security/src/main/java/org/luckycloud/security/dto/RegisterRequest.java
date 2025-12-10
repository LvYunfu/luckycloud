package org.luckycloud.security.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String userName;

    private String password;

    private String mail;

    private String profile;

    private String avatar;

    private String verifyCode;

    private String verifyId;
}
