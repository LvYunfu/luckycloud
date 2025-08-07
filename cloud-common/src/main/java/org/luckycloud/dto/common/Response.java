package org.luckycloud.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.luckycloud.exception.BusinessException;

import static org.luckycloud.dto.common.ResponseCode.DATA_SUCCESS;
import static org.luckycloud.dto.common.ResponseCode.SUCCESS;

/**
 * @author lvyf
 * @description:
 * @date 2023/4/8 0:25
 */
@Data
@AllArgsConstructor
public class Response<T> {
    /**
     * 返回码
     */
    String code;
    /**
     * 返回信息
     */
    String message;

    /**
     * 返回数据
     */
    T data;


    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public static Response<Void> success(String message) {
        return new Response<>(SUCCESS.getCode(), message);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(DATA_SUCCESS.getCode(), DATA_SUCCESS.getMessage(), data);
    }
    public static <T> Response<T> success(T data,String message) {
        return new Response<>(DATA_SUCCESS.getCode(), message, data);
    }

    public static Response<Void> exception(BusinessException e) {
        return new Response<>(e.getCode(), e.getMessage());
    }

    public static Response<Void> fail(String defaultMessage) {
        return new Response<>(ResponseCode.OPERATE_FAILED.getCode(), defaultMessage);
    }
}
