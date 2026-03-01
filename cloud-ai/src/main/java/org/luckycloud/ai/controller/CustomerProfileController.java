package org.luckycloud.ai.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.domain.CustomerProfile;
import org.luckycloud.ai.dto.TrainingResponse;
import org.luckycloud.ai.service.CustomerProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户画像管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class CustomerProfileController {
    
    private final CustomerProfileService customerProfileService;
    
    /**
     * 获取所有客户画像
     */
    @GetMapping
    public List<CustomerProfile> getAllProfiles() {
        log.info("收到获取所有客户画像请求");
        return customerProfileService.getAllProfiles();
    }
    
    /**
     * 根据ID获取客户画像
     */
    @GetMapping("/{profileId}")
    public CustomerProfile getProfileById(@PathVariable String profileId) {
        log.info("收到获取客户画像请求，profileId: {}", profileId);
        return customerProfileService.getProfileById(profileId);
    }
    
    /**
     * 根据职业类型筛选客户画像
     */
    @GetMapping("/occupation/{occupation}")
    public List<CustomerProfile> getProfilesByOccupation(@PathVariable String occupation) {
        log.info("收到按职业筛选客户画像请求，occupation: {}", occupation);
        return customerProfileService.getProfilesByOccupation(occupation);
    }
    
    /**
     * 根据沟通风格筛选客户画像
     */
    @GetMapping("/communication-style/{style}")
    public List<CustomerProfile> getProfilesByCommunicationStyle(@PathVariable String style) {
        log.info("收到按沟通风格筛选客户画像请求，style: {}", style);
        return customerProfileService.getProfilesByCommunicationStyle(style);
    }
    
    /**
     * 根据难度等级范围筛选客户画像
     */
    @GetMapping("/difficulty-range")
    public List<CustomerProfile> getProfilesByDifficultyRange(
            @RequestParam Integer minDifficulty,
            @RequestParam Integer maxDifficulty) {
        log.info("收到按难度范围筛选客户画像请求，min: {}, max: {}", minDifficulty, maxDifficulty);
        return customerProfileService.getProfilesByDifficultyRange(minDifficulty, maxDifficulty);
    }
    
    /**
     * 创建新的客户画像
     */
    @PostMapping
    public CustomerProfile createProfile(@RequestBody CustomerProfile profile) {
        log.info("收到创建客户画像请求");
        return customerProfileService.createProfile(profile);
    }
    
    /**
     * 更新客户画像
     */
    @PutMapping("/{profileId}")
    public CustomerProfile updateProfile(@PathVariable String profileId, @RequestBody CustomerProfile profile) {
        log.info("收到更新客户画像请求，profileId: {}", profileId);
        return customerProfileService.updateProfile(profileId, profile);
    }
    
    /**
     * 删除客户画像
     */
    @DeleteMapping("/{profileId}")
    public TrainingResponse deleteProfile(@PathVariable String profileId) {
        log.info("收到删除客户画像请求，profileId: {}", profileId);
        boolean success = customerProfileService.deleteProfile(profileId);
        if (success) {
            return TrainingResponse.success(profileId, "客户画像删除成功");
        } else {
            return TrainingResponse.error("客户画像删除失败");
        }
    }
    
    /**
     * 搜索客户画像
     */
    @GetMapping("/search")
    public List<CustomerProfile> searchProfiles(@RequestParam String keyword) {
        log.info("收到搜索客户画像请求，keyword: {}", keyword);
        return customerProfileService.searchProfiles(keyword);
    }
    
    /**
     * 重新加载客户画像配置
     */
    @PostMapping("/reload")
    public TrainingResponse reloadProfiles() {
        log.info("收到重新加载客户画像配置请求");
        try {
            ((org.luckycloud.ai.service.impl.CustomerProfileServiceImpl) customerProfileService).reloadProfiles();
            return TrainingResponse.success("reload", "客户画像配置重新加载成功");
        } catch (Exception e) {
            log.error("重新加载客户画像配置失败", e);
            return TrainingResponse.error("重新加载失败: " + e.getMessage());
        }
    }
}