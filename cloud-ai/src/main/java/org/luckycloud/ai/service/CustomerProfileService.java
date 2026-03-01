package org.luckycloud.ai.service;

import org.luckycloud.ai.domain.CustomerProfile;

import java.util.List;

/**
 * 客户画像管理服务接口
 */
public interface CustomerProfileService {
    
    /**
     * 获取所有客户画像
     * @return 客户画像列表
     */
    List<CustomerProfile> getAllProfiles();
    
    /**
     * 根据ID获取客户画像
     * @param profileId 画像ID
     * @return 客户画像
     */
    CustomerProfile getProfileById(String profileId);
    
    /**
     * 根据职业类型筛选客户画像
     * @param occupation 职业类型
     * @return 匹配的客户画像列表
     */
    List<CustomerProfile> getProfilesByOccupation(String occupation);
    
    /**
     * 根据沟通风格筛选客户画像
     * @param communicationStyle 沟通风格
     * @return 匹配的客户画像列表
     */
    List<CustomerProfile> getProfilesByCommunicationStyle(String communicationStyle);
    
    /**
     * 根据难度等级范围筛选客户画像
     * @param minDifficulty 最小难度
     * @param maxDifficulty 最大难度
     * @return 匹配的客户画像列表
     */
    List<CustomerProfile> getProfilesByDifficultyRange(Integer minDifficulty, Integer maxDifficulty);
    
    /**
     * 创建新的客户画像
     * @param profile 客户画像对象
     * @return 创建成功的客户画像
     */
    CustomerProfile createProfile(CustomerProfile profile);
    
    /**
     * 更新客户画像
     * @param profileId 画像ID
     * @param profile 更新的客户画像对象
     * @return 更新后的客户画像
     */
    CustomerProfile updateProfile(String profileId, CustomerProfile profile);
    
    /**
     * 删除客户画像
     * @param profileId 画像ID
     * @return 是否删除成功
     */
    boolean deleteProfile(String profileId);
    
    /**
     * 搜索客户画像
     * @param keyword 搜索关键词
     * @return 匹配的客户画像列表
     */
    List<CustomerProfile> searchProfiles(String keyword);
}