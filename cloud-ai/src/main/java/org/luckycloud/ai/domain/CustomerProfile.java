package org.luckycloud.ai.domain;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 客户画像实体类
 * 用于定义AI扮演的客户特征
 */
@Data
public class CustomerProfile {
    
    /**
     * 画像ID
     */
    private String profileId;
    
    /**
     * 客户姓名
     */
    private String customerName;
    
    /**
     * 客户年龄
     */
    private Integer age;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 职业
     */
    private String occupation;
    
    /**
     * 收入水平
     */
    private IncomeLevel incomeLevel;
    
    /**
     * 消费习惯
     */
    private List<String> consumptionHabits;
    
    /**
     * 痛点问题
     */
    private List<String> painPoints;
    
    /**
     * 沟通风格
     */
    private CommunicationStyle communicationStyle;
    
    /**
     * 刁难程度（1-10）
     */
    private Integer difficultyLevel;
    
    /**
     * 特殊要求或背景信息
     */
    private Map<String, Object> additionalInfo;
    
    /**
     * 角色设定提示词
     */
    private String rolePrompt;
    
    public enum IncomeLevel {
        LOW("低收入"),
        MEDIUM("中等收入"),
        HIGH("高收入"),
        VERY_HIGH("超高收入");
        
        private final String description;
        
        IncomeLevel(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    public enum CommunicationStyle {
        DIRECT("直接型"),
        INDIRECT("委婉型"),
        AGGRESSIVE("激进型"),
        ANALYTICAL("分析型"),
        EMOTIONAL("情绪化");
        
        private final String description;
        
        CommunicationStyle(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
}