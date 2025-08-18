package org.luckycloud.exception;

import lombok.Getter;
import org.luckycloud.dto.common.ResponseCode;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author lvyf
 * @description:
 * @date 2025/7/30
 */
@Getter
public class BusinessException extends RuntimeException {
    /**
     * 异常码
     */
    String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResponseCode code, String message) {
        super(ObjectUtils.isEmpty(message) ? code.getMessage() : message);
        this.code = code.getCode();
    }

    public BusinessException(ResponseCode code) {
        super(code.getMessage());
        this.code = code.getCode();
    }
}
