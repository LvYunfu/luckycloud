package org.luckycloud.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI 生成图片响应
 *
 * @author lvyf
 * @date 2026/7/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenerateImageResponse {

    /**
     * 文件唯一标识
     */
    private String fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String fileUrl;
}
