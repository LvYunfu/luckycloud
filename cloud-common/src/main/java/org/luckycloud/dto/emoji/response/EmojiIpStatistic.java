package org.luckycloud.dto.emoji.response;

import lombok.Data;

/**
 * 角色IP响应
 */
@Data
public class EmojiIpStatistic {

    private int total;

    private String aiGenerated;

    private String userUpload;


}
