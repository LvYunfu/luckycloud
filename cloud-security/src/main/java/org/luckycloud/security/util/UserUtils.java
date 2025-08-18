package org.luckycloud.security.util;

import org.luckycloud.dto.common.ResponseCode;
import org.luckycloud.exception.BusinessException;
import org.luckycloud.security.entity.SysUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/19
 */
public class UserUtils {


    public static String getUserId() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return ((SysUserDetail) auth.getPrincipal()).getUserId();
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED);
        }
    }
    public static String getUserName() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return ((SysUserDetail) auth.getPrincipal()).getUsername();
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED);
        }
    }
    public static SysUserDetail getUserBaseInfo() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (SysUserDetail) auth.getPrincipal();
        } catch (Exception e) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED);
        }
    }
}
