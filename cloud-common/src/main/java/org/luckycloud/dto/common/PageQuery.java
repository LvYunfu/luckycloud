package org.luckycloud.dto.common;

import lombok.Data;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/9
 */
@Data
public class PageQuery {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private String sortField;

    private String sortOrder = "desc";

    public PageQuery(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public PageQuery() {
    }

}
