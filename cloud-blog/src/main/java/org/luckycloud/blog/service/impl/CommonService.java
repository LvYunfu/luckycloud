package org.luckycloud.blog.service.impl;

import org.luckycloud.dto.common.UploadFileDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/3
 */
public interface CommonService {
    UploadFileDTO uploadFile(MultipartFile file);
}
