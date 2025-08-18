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

    DATA_SUCCESS("LC001", "操作成功"),

    OPERATE_SUCCESS("LC002", "操作成功"),

    OPERATE_FAILED("LC003", "操作失败"),

    OPERATE_WARN("LC004", "操作异常"),

    VERIFY("LC005", "校验成功"),

    VALIDATE_FAILED("LC006", "参数检验失败"),

    SYS_ERROR("LC007", "系统异常,请联系管理员"),

    UNAUTHORIZED("LC008", "暂未登录或登录失效,请重新登录"),
    ;

    private String code;

    private String message;

    ResponseCode(String code, String message) {
        this.code=code;
        this.message=message;
    }


}
