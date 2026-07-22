package org.luckycloud.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.luckycloud.dto.common.UploadFileDTO;
import org.luckycloud.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.luckycloud.dto.common.ResponseCode.OPERATE_FAILED;


/**
 * @author lvyf
 * @description:
 * @date 2025/11/26
 */
@Component
@Log4j2
public class UploadUtils {

    private UploadUtils() {
    }

    @Value("${lucky.cloud.file.github-cdn}")
    String githubCdn;

    @Value("${lucky.cloud.file.github-token}")
    private String gitHubToken;

    @Value("${lucky.cloud.file.file-path}")
    private String filePath;


    public static final String GITHUB_PATH = "/";


    public static String getFilePath(FileInfo fileInfo) {
        return String.join(GITHUB_PATH, List.of("images", fileInfo.getPrefix(), fileInfo.getUserId(), fileInfo.getFileId()));
    }


    public static String getFilePath(String userId,String prefix, String fileId) {
        return String.join(GITHUB_PATH, List.of("images",prefix, userId, fileId));
    }

    public String getFileUrl(FileInfo fileInfo) {
        return githubCdn + getFilePath(fileInfo);
    }

    public String getFileUrl(String userId, String prefix ,String fileId) {
        if (ObjectUtils.isEmpty(fileId)) {
            return null;
        }
        return githubCdn + getFilePath(userId, prefix,fileId);
    }


    public UploadFileDTO uploadFile(FileInfo fileInfo) {
        try {
            MultipartFile file = fileInfo.getFile();
            GitHub github = new GitHubBuilder().withOAuthToken(gitHubToken).build();
            GHRepository repository = github.getRepository(filePath);
            fileInfo.fillFileName(fileInfo.getFile().getOriginalFilename());
            String path = UploadUtils.getFilePath(fileInfo);
            log.info("上传文件路径:{}", path);
            // 上传文件
            repository.createContent()
                    .message("lucky upload userId:" + fileInfo.getUserId())
                    .path(path)
                    .content(file.getInputStream().readAllBytes())
                    .commit();

            return new UploadFileDTO(file.getOriginalFilename(), this.getFileUrl(fileInfo), fileInfo.getFileId());
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new BusinessException(OPERATE_FAILED, "上传文件失败");
        }
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileInfo {

        private MultipartFile file;

        private String userId;

        private String fileId;

        private String prefix;

        private String name;

        private String type;

        public void fillFileName(String fullName) {
            String[] split = fullName.split("\\.");
            this.name = split[0];
            this.type = split[1];
            this.fileId = name + "_" + UUID.randomUUID().toString().replace("-", "") + "." + type;
        }

    }
}
