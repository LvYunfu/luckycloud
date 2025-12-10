package org.luckycloud.constant;

/**
 * @author lvyf
 * @description:
 * @date 2025/6/4
 */
public class SystemConstant {
    public static final String ENABLE = "1";
    public static final String DISABLE = "0";

    public static final String N = "N";
    public static final String Y = "Y";

    public static final class VerifyType {
        private VerifyType() {
        }

        public static final String REGISTER_VERIFY_CODE = "VT00";

        public static final String FORGET_PASSWORD_VERIFY_CODE = "VT01";

        public static final String BIND_ACCOUNT_VERIFY_CODE = "VT02";

    }
}
