package org.luckycloud.blog.controller;

import jakarta.annotation.Resource;
import org.luckycloud.blog.service.impl.CommonService;
import org.luckycloud.dto.common.Response;
import org.luckycloud.dto.common.UploadFileDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/3
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    CommonService   commonService  ;
    /**
     * 上传文件
     */
    @PostMapping("/upload-file")
    public Response<UploadFileDTO> uploadFile(MultipartFile file) {
        return Response.successData(commonService.uploadFile(file));
    }
}
