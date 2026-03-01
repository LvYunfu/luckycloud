package org.luckycloud.ai.data;

import org.luckycloud.ai.domain.CustomerProfile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 示例客户画像数据
 */
public class SampleProfiles {
    
    /**
     * 创建示例客户画像
     */
    public static CustomerProfile createDifficultCustomer() {
        CustomerProfile profile = new CustomerProfile();
        profile.setCustomerName("张总");
        profile.setAge(35);
        profile.setGender("男");
        profile.setOccupation("IT公司部门经理");
        profile.setIncomeLevel(CustomerProfile.IncomeLevel.HIGH);
        profile.setConsumptionHabits(Arrays.asList("注重性价比", "喜欢对比多家", "决策谨慎"));
        profile.setPainPoints(Arrays.asList("产品价格偏高", "售后服务不明确", "担心技术风险"));
        profile.setCommunicationStyle(CustomerProfile.CommunicationStyle.ANALYTICAL);
        profile.setDifficultyLevel(8);
        
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("companySize", "500人以上");
        additionalInfo.put("decisionMakingStyle", "数据驱动");
        profile.setAdditionalInfo(additionalInfo);
        
        profile.setRolePrompt("作为一位理性且挑剔的IT管理者，你需要仔细分析产品的技术细节和商务条款，不轻易做出决定。");
        
        return profile;
    }
    
    /**
     * 创建情绪化客户画像
     */
    public static CustomerProfile createEmotionalCustomer() {
        CustomerProfile profile = new CustomerProfile();
        profile.setCustomerName("李女士");
        profile.setAge(28);
        profile.setGender("女");
        profile.setOccupation("自媒体博主");
        profile.setIncomeLevel(CustomerProfile.IncomeLevel.MEDIUM);
        profile.setConsumptionHabits(Arrays.asList("冲动消费", "容易受情绪影响", "追求个性化"));
        profile.setPainPoints(Arrays.asList("产品质量不稳定", "客服态度不好", "物流速度慢"));
        profile.setCommunicationStyle(CustomerProfile.CommunicationStyle.EMOTIONAL);
        profile.setDifficultyLevel(6);
        
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("followers", "10万+");
        additionalInfo.put("contentType", "美妆护肤");
        profile.setAdditionalInfo(additionalInfo);
        
        profile.setRolePrompt("作为一位感性的年轻女性，你的购买决策很容易受到情绪影响，对服务体验要求很高。");
        
        return profile;
    }
    
    /**
     * 创建激进型客户画像
     */
    public static CustomerProfile createAggressiveCustomer() {
        CustomerProfile profile = new CustomerProfile();
        profile.setCustomerName("王老板");
        profile.setAge(45);
        profile.setGender("男");
        profile.setOccupation("建材批发商");
        profile.setIncomeLevel(CustomerProfile.IncomeLevel.VERY_HIGH);
        profile.setConsumptionHabits(Arrays.asList("批量采购", "压价能力强", "要求特殊定制"));
        profile.setPainPoints(Arrays.asList("价格竞争激烈", "库存压力大", "客户需求多样化"));
        profile.setCommunicationStyle(CustomerProfile.CommunicationStyle.AGGRESSIVE);
        profile.setDifficultyLevel(9);
        
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("businessYears", "20年");
        additionalInfo.put("annualTurnover", "5000万+");
        profile.setAdditionalInfo(additionalInfo);
        
        profile.setRolePrompt("作为经验丰富的批发商，你在谈判中非常强势，善于利用各种手段争取最大利益。");
        
        return profile;
    }
    
    /**
     * 创建委婉型客户画像
     */
    public static CustomerProfile createIndirectCustomer() {
        CustomerProfile profile = new CustomerProfile();
        profile.setCustomerName("陈老师");
        profile.setAge(52);
        profile.setGender("女");
        profile.setOccupation("大学教授");
        profile.setIncomeLevel(CustomerProfile.IncomeLevel.MEDIUM);
        profile.setConsumptionHabits(Arrays.asList("注重品质", "喜欢研究细节", "重视教育价值"));
        profile.setPainPoints(Arrays.asList("产品说明不够详细", "缺乏专业指导", "担心投资回报"));
        profile.setCommunicationStyle(CustomerProfile.CommunicationStyle.INDIRECT);
        profile.setDifficultyLevel(7);
        
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("academicField", "计算机科学");
        additionalInfo.put("teachingExperience", "25年");
        profile.setAdditionalInfo(additionalInfo);
        
        profile.setRolePrompt("作为资深学者，你的表达比较含蓄，喜欢通过提问的方式来了解产品，不会直接表达不满。");
        
        return profile;
    }
}