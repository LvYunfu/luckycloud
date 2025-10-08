package org.luckycloud.constant;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/7
 */
public class BlogConstant {

    public static final String GITHUB_PATH = "/";
    private BlogConstant() {
    }

    public static class BlogStatus {
        public static final String DRAFT = "BS00"; // 草稿
        public static final String PUBLIC = "BS01"; // 公开
        public static final String PRIVATE = "BS02"; // 私有
    }

    public static class BlogOperateType {
        public static final String VIEW = "OT00";
        public static final String LIKE = "OT01";
        public static final String COLLECT = "OT02";
    }
}
