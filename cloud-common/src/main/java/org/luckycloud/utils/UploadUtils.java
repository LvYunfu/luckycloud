package org.luckycloud.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author lvyf
 * @description:
 * @date 2025/11/26
 */
@Component
public class UploadUtils {

    private UploadUtils() {
    }

    @Value("${lucky.cloud.file.github-cdn}")
    String githubCdn;
    public static final String GITHUB_PATH = "/";


    public static String getFilePath(String userId, String fileId) {
        return String.join(GITHUB_PATH, List.of("images", userId, fileId));
    }

    public static FileInfo getFileInfo(String fullName) {
        String[] split = fullName.split("\\.");
        return new FileInfo(split[0], split[1]);
    }

    public String getFileUrl(String userId, String fileId) {
        return githubCdn + getFilePath(userId, fileId);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileInfo {
        private String name;
        private String type;

    }
}
