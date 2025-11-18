package org.luckycloud.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Select {

    private String label;

    private String value;

    private List<Select> children;


    public Select(String label, String value) {
        this.label = label;
        this.value = value;
    }
}
