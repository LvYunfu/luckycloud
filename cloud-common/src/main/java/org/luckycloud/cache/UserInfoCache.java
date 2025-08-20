package org.luckycloud.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.Resource;
import org.luckycloud.domain.user.CloudUserInfoDO;
import org.luckycloud.mapper.user.CloudUserInfoMapper;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author lvyf
 * @description:
 * @date 2025/8/20
 */
@Component
public class UserInfoCache {

    //实现缓存
    private Cache<String, Object> userCaChe() {
        return Caffeine.newBuilder()
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(1000)
                .build();
    }

    @Resource
    CloudUserInfoMapper userInfoMapper;
    public CloudUserInfoDO getUserInfo(String userId) {
        Function<String, CloudUserInfoDO> function = user -> userInfoMapper.findByUserId(user);
        return (CloudUserInfoDO) userCaChe().get(userId,function);
    }

    public String getUserName (String userId) {
        Function<String, CloudUserInfoDO> function = user -> userInfoMapper.findByUserId(user);
        CloudUserInfoDO userInfoDO =  (CloudUserInfoDO) userCaChe().get(userId,function);
        if( userInfoDO != null) {
            return userInfoDO.getUserName();
        }
        return "";
    }


}
