package org.luckycloud.blog.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.luckycloud.dto.common.UploadFileDTO;
import org.luckycloud.security.util.UserUtils;
import org.luckycloud.utils.UploadUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/3
 */
@Service
@Log4j2
public class CommonServiceImpl implements CommonService {

    @Resource
    private UploadUtils uploadUtils;

    @Override
    public UploadFileDTO uploadFile(MultipartFile file,String type){

        UploadUtils.FileInfo fileInfo = new UploadUtils.FileInfo();
        fileInfo.setFile(file);
        fileInfo.setUserId(UserUtils.getUserId());
        fileInfo.setPrefix(type);
        return uploadUtils.uploadFile(fileInfo);

    }
}
