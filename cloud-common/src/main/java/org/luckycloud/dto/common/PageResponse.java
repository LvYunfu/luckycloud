package org.luckycloud.dto.common;

import lombok.Data;

import java.util.List;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
@Data
public class PageResponse<T> {


    private long total;

    private List<T> list;

    public PageResponse(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public PageResponse() {
    }
}
