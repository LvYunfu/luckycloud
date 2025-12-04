package org.luckycloud.constant;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/7
 */
public class BlogConstant {



    private BlogConstant() {
    }

    public static class BlogStatus {
        private BlogStatus() {
        }

        public static final String PRIVATE = "BS00"; // 私有
        public static final String PUBLIC = "BS01"; // 公开
    }

    public static class BlogOperateType {
        private BlogOperateType() {
        }

        public static final String VIEW = "OT00";
        public static final String LIKE = "OT01";
        public static final String COLLECT = "OT02";
    }
}
