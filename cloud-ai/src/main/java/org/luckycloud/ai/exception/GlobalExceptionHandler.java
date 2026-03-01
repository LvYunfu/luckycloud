package org.luckycloud.ai.exception;

import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.dto.TrainingResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 处理业务异常
     */
    @ExceptionHandler(RuntimeException.class)
    public TrainingResponse handleRuntimeException(RuntimeException e) {
        log.error("业务异常", e);
        return TrainingResponse.error("系统繁忙，请稍后重试: " + e.getMessage());
    }
    
    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public TrainingResponse handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数异常", e);
        return TrainingResponse.error("参数错误: " + e.getMessage());
    }
    
    /**
     * 处理通用异常
     */
    @ExceptionHandler(Exception.class)
    public TrainingResponse handleException(Exception e) {
        log.error("系统异常", e);
        return TrainingResponse.error("系统内部错误，请联系管理员");
    }
}