package org.luckycloud.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/10
 */
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public  static  String toJsonString(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON string", e);
        }
    }
}
