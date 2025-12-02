package org.luckycloud.blog.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.luckycloud.dto.common.UploadFileDTO;
import org.luckycloud.exception.BusinessException;
import org.luckycloud.security.util.UserUtils;
import org.luckycloud.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.luckycloud.dto.common.ResponseCode.OPERATE_FAILED;

/**
 * @author lvyf
 * @description:
 * @date 2025/12/3
 */
@Service
@Log4j2
public class CommonServiceImpl implements CommonService {


    @Value("${lucky.cloud.file.github-token}")
    private String gitHubToken;

    @Value("${lucky.cloud.file.file-path}")
    private String filePath;


    @Resource
    private UploadUtils uploadUtils;

    @Override
    public UploadFileDTO uploadFile(MultipartFile file) {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(gitHubToken).build();
            GHRepository repository = github.getRepository(filePath);
            String userId = UserUtils.getUserId();
            UploadUtils.FileInfo fileInfo = UploadUtils.getFileInfo(file.getOriginalFilename());
            String fileId = fileInfo.getName() + "_" + UUID.randomUUID().toString().replace("-", "") + "." + fileInfo.getType();
            String path = UploadUtils.getFilePath(userId, fileId);
            log.info("上传文件路径:{}", path);
            // 上传文件
            repository.createContent()
                    .message("lucky blog upload userId:" + userId)
                    .path(path)
                    .content(file.getInputStream().readAllBytes())
                    .commit();

            return new UploadFileDTO(file.getOriginalFilename(), uploadUtils.getFileUrl(userId, fileId), fileId);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new BusinessException(OPERATE_FAILED, "上传文件失败");
        }
    }
}
