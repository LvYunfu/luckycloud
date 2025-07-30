package org.luckycloud.dto.common;

import lombok.Getter;

/**
 * @author lvyf
 * @description:
 * @date 2023/4/8 0:53
 */

@Getter
public enum ResponseCode {

    SUCCESS("LC000", "操作成功"),

    OPERATE_SUCCESS("LC001", "操作成功"),

    OPERATE_FAILED("LC002", "操作失败"),

    OPERATE_WARN("LC003", "操作异常"),

    VERIFY("LC004", "校验成功"),

    VALIDATE_FAILED("LC005", "参数检验失败"),

    SYS_ERROR("LC006", "系统异常,请联系管理员"),;

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code=code;
        this.message=message;
    }


}
