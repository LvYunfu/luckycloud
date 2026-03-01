package org.luckycloud.ai.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.luckycloud.ai.domain.CustomerProfile;
import org.luckycloud.ai.service.CustomerProfileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 客户画像管理服务实现
 */
@Slf4j
@Service
public class CustomerProfileServiceImpl implements CustomerProfileService {
    
    @Value("${ai.profiles.config-path:profiles/customer-profiles.json}")
    private String profilesConfigPath;
    
    private final Map<String, CustomerProfile> profileCache = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @PostConstruct
    public void init() {
        loadProfilesFromConfig();
    }
    
    @Override
    public List<CustomerProfile> getAllProfiles() {
        return new ArrayList<>(profileCache.values());
    }
    
    @Override
    public CustomerProfile getProfileById(String profileId) {
        if (!StringUtils.hasText(profileId)) {
            return null;
        }
        return profileCache.get(profileId);
    }
    
    @Override
    public List<CustomerProfile> getProfilesByOccupation(String occupation) {
        if (!StringUtils.hasText(occupation)) {
            return Collections.emptyList();
        }
        
        return profileCache.values().stream()
                .filter(profile -> occupation.equals(profile.getOccupation()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CustomerProfile> getProfilesByCommunicationStyle(String communicationStyle) {
        if (!StringUtils.hasText(communicationStyle)) {
            return Collections.emptyList();
        }
        
        return profileCache.values().stream()
                .filter(profile -> {
                    if (profile.getCommunicationStyle() == null) return false;
                    return communicationStyle.equals(profile.getCommunicationStyle().name());
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<CustomerProfile> getProfilesByDifficultyRange(Integer minDifficulty, Integer maxDifficulty) {
        if (minDifficulty == null || maxDifficulty == null) {
            return Collections.emptyList();
        }
        
        return profileCache.values().stream()
                .filter(profile -> {
                    if (profile.getDifficultyLevel() == null) return false;
                    int level = profile.getDifficultyLevel();
                    return level >= minDifficulty && level <= maxDifficulty;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public CustomerProfile createProfile(CustomerProfile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("客户画像不能为空");
        }
        
        // 生成唯一ID
        if (profile.getProfileId() == null) {
            profile.setProfileId(UUID.randomUUID().toString());
        }
        
        // 设置默认值
        if (profile.getDifficultyLevel() == null) {
            profile.setDifficultyLevel(5);
        }
        
        profileCache.put(profile.getProfileId(), profile);
        log.info("创建客户画像成功，ID: {}", profile.getProfileId());
        return profile;
    }
    
    @Override
    public CustomerProfile updateProfile(String profileId, CustomerProfile profile) {
        if (!StringUtils.hasText(profileId) || profile == null) {
            throw new IllegalArgumentException("画像ID和客户画像对象都不能为空");
        }
        
        CustomerProfile existingProfile = profileCache.get(profileId);
        if (existingProfile == null) {
            throw new IllegalArgumentException("客户画像不存在，ID: " + profileId);
        }
        
        // 更新字段
        if (profile.getCustomerName() != null) {
            existingProfile.setCustomerName(profile.getCustomerName());
        }
        if (profile.getAge() != null) {
            existingProfile.setAge(profile.getAge());
        }
        if (profile.getGender() != null) {
            existingProfile.setGender(profile.getGender());
        }
        if (profile.getOccupation() != null) {
            existingProfile.setOccupation(profile.getOccupation());
        }
        if (profile.getIncomeLevel() != null) {
            existingProfile.setIncomeLevel(profile.getIncomeLevel());
        }
        if (profile.getConsumptionHabits() != null) {
            existingProfile.setConsumptionHabits(profile.getConsumptionHabits());
        }
        if (profile.getPainPoints() != null) {
            existingProfile.setPainPoints(profile.getPainPoints());
        }
        if (profile.getCommunicationStyle() != null) {
            existingProfile.setCommunicationStyle(profile.getCommunicationStyle());
        }
        if (profile.getDifficultyLevel() != null) {
            existingProfile.setDifficultyLevel(profile.getDifficultyLevel());
        }
        if (profile.getAdditionalInfo() != null) {
            existingProfile.setAdditionalInfo(profile.getAdditionalInfo());
        }
        if (profile.getRolePrompt() != null) {
            existingProfile.setRolePrompt(profile.getRolePrompt());
        }
        
        log.info("更新客户画像成功，ID: {}", profileId);
        return existingProfile;
    }
    
    @Override
    public boolean deleteProfile(String profileId) {
        if (!StringUtils.hasText(profileId)) {
            return false;
        }
        
        CustomerProfile removed = profileCache.remove(profileId);
        if (removed != null) {
            log.info("删除客户画像成功，ID: {}", profileId);
            return true;
        }
        return false;
    }
    
    @Override
    public List<CustomerProfile> searchProfiles(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return Collections.emptyList();
        }
        
        String lowerKeyword = keyword.toLowerCase();
        return profileCache.values().stream()
                .filter(profile -> 
                    (profile.getCustomerName() != null && profile.getCustomerName().toLowerCase().contains(lowerKeyword)) ||
                    (profile.getOccupation() != null && profile.getOccupation().toLowerCase().contains(lowerKeyword)) ||
                    (profile.getGender() != null && profile.getGender().toLowerCase().contains(lowerKeyword))
                )
                .collect(Collectors.toList());
    }
    
    /**
     * 从配置文件加载客户画像
     */
    private void loadProfilesFromConfig() {
        try {
            ClassPathResource resource = new ClassPathResource(profilesConfigPath);
            if (!resource.exists()) {
                log.warn("客户画像配置文件不存在: {}", profilesConfigPath);
                return;
            }
            
            try (InputStream inputStream = resource.getInputStream()) {
                CustomerProfile[] profiles = objectMapper.readValue(inputStream, CustomerProfile[].class);
                
                for (CustomerProfile profile : profiles) {
                    // 验证必要字段
                    if (profile.getProfileId() == null) {
                        profile.setProfileId(UUID.randomUUID().toString());
                    }
                    
                    profileCache.put(profile.getProfileId(), profile);
                }
                
                log.info("成功加载 {} 个客户画像配置", profiles.length);
            }
            
        } catch (IOException e) {
            log.error("加载客户画像配置文件失败: {}", profilesConfigPath, e);
        } catch (Exception e) {
            log.error("解析客户画像配置文件时发生错误", e);
        }
    }
    
    /**
     * 重新加载配置文件（用于运行时刷新）
     */
    public void reloadProfiles() {
        profileCache.clear();
        loadProfilesFromConfig();
        log.info("客户画像配置已重新加载");
    }
}