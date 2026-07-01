package org.luckycloud.ai.dto;

import lombok.Data;

/**
 * 通用删除请求
 */
@Data
public class DeleteRequest {
    /**
     * 要删除的ID
     */
    private String id;
}
