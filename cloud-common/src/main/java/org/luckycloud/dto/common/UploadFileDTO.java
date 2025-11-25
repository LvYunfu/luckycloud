package org.luckycloud.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lvyf
 * @description:
 * @date 2025/11/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileDTO {


    private String fileName;

    private String fileUrl;

    private String fileId;
}


