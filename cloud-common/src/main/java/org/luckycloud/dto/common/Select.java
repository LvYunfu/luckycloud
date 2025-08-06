package org.luckycloud.dto.common;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/6
 */
@Data
public class Select {

    private String label;
    private String value;

    public Select(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public Select() {
    }
}
