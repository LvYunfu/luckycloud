package org.luckycloud.ai.domain;

import lombok.Data;
import org.luckycloud.ai.enums.AiModelEnum;
import org.luckycloud.ai.domain.EvaluationResult;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 培训会话实体类
 */
@Data
public class TrainingSession {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 客户画像ID
     */
    private String profileId;

    /**
     * 使用的AI模型
     */
    private AiModelEnum aiModel;

    /**
     * 会话开始时间
     */
    private LocalDateTime startTime;

    /**
     * 会话结束时间
     */
    private LocalDateTime endTime;

    /**
     * 对话历史记录
     */
    private List<Message> messages;

    /**
     * 会话状态
     */
    private SessionStatus status;

    /**
     * 测评结果列表
     */
    private List<EvaluationResult> evaluations;

    public TrainingSession() {
        this.sessionId = UUID.randomUUID().toString();
        this.startTime = LocalDateTime.now();
        this.messages = new ArrayList<>();
        this.evaluations = new ArrayList<>();
        this.status = SessionStatus.ACTIVE;
    }

    public TrainingSession(String userId, String profileId, AiModelEnum aiModel) {
        this();
        this.userId = userId;
        this.profileId = profileId;
        this.aiModel = aiModel;
    }

    /**
     * 添加用户消息
     */
    public void addUserMessage(String content) {
        Message message = new UserMessage( content);
        this.messages.add(message);
    }

    /**
     * 添加AI回复
     */
    public void addAiMessage(String content) {
        Message message = new AssistantMessage(content);
        this.messages.add(message);
    }

    /**
     * 结束会话
     */
    public void endSession() {
        this.endTime = LocalDateTime.now();
        this.status = SessionStatus.COMPLETED;
    }

    /**
     * 消息实体类
     */
//    @Data
//    public static class Message {
//        private Role role;
//        private String content;
//        private LocalDateTime timestamp;
//
//        public enum Role {
//            USER, AI
//        }
//    }

    /**
     * 会话状态枚举
     */
    public enum SessionStatus {
        ACTIVE("进行中"),
        PAUSED("已暂停"),
        COMPLETED("已完成"),
        EXPIRED("已过期");

        private final String description;

        SessionStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
