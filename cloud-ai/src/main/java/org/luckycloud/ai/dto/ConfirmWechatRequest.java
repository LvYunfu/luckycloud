package org.luckycloud.ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 确认微信手动上传完成请求
 */
@Data
public class ConfirmWechatRequest {
    /**
     * 微信同步任务 ID
     */
    @NotBlank(message = "任务ID不能为空")
    private String taskId;
}
