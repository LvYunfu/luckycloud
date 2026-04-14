package org.luckycloud.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

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

    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON string", e);
        }
    }

    public static <T> List<T> parseList(String json, TypeReference<List<T>> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON to List", e);
        }
    }
}
