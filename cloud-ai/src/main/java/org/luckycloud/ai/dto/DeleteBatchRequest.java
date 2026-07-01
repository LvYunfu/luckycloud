package org.luckycloud.ai.dto;

import lombok.Data;

import java.util.List;

/**
 * 批量删除请求
 */
@Data
public class DeleteBatchRequest {
    /**
     * 要删除的ID列表
     */
    private List<String> ids;
}
