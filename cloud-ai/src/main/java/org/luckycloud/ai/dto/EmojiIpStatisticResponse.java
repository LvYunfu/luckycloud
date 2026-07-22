package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 角色IP响应
 */
@Data
public class EmojiIpStatisticResponse {

    private int total;

    private String aiGenerated;

    private String userUpload;


}
