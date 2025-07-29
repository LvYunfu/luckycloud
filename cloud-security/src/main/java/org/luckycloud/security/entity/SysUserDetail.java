package org.luckycloud.security.entity;

import lombok.Builder;
import lombok.Getter;
import org.luckycloud.domain.user.CloudUserInfoDO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.luckycloud.constant.SystemConstant.ENABLE;

@Getter
@Builder
public class SysUserDetail implements UserDetails {

    private String userId;

    private String username;

    private String accountName;

    private String password;

    private boolean enabled;

    private boolean accountNonLocked;

    private List<?> resourceInfoList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static SysUserDetail convertToSysUser(CloudUserInfoDO userInfoDO) {
        if (userInfoDO == null) {
            return null;
        }
        SysUserDetail sysUserDetail = SysUserDetail.builder()
                .userId(userInfoDO.getUserId())
                .username(userInfoDO.getUserName())
                .accountName(userInfoDO.getUserName())
                .password(userInfoDO.getPassword())
                .enabled(ENABLE.equals(userInfoDO.getStatus()))
                .accountNonLocked(ENABLE.equals(userInfoDO.getAccountStatus()))
                .build();
        return sysUserDetail;
    }
}