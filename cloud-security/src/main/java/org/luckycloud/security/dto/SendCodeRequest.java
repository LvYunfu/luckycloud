package org.luckycloud.security.dto;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/10
 */
@Data
public class SendCodeRequest {
    private String mail;
    private String type;

}
