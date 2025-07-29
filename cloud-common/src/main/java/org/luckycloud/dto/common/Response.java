package org.luckycloud.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

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


    public static Response<Void> success(String message) {
        return new Response<>("", message);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>("", message, data);
    }

}
