package org.luckycloud.ai.utils;

import org.luckycloud.ai.domain.TrainingSession;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话管理器工具类
 * 负责会话的生命周期管理和清理
 */
@Component
public class SessionManager {
    
    // 会话存储
    private final Map<String, TrainingSession> sessions = new ConcurrentHashMap<>();
    
    // 会话过期时间（分钟）
    private static final int SESSION_EXPIRE_MINUTES = 30;
    
    /**
     * 存储会话
     */
    public void storeSession(TrainingSession session) {
        sessions.put(session.getSessionId(), session);
    }
    
    /**
     * 获取会话
     */
    public TrainingSession getSession(String sessionId) {
        return sessions.get(sessionId);
    }
    
    /**
     * 移除会话
     */
    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }
    
    /**
     * 清理会话
     */
    public void cleanupExpiredSessions() {
        LocalDateTime expireTime = LocalDateTime.now().minusMinutes(SESSION_EXPIRE_MINUTES);
        Iterator<Map.Entry<String, TrainingSession>> iterator = sessions.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, TrainingSession> entry = iterator.next();
            TrainingSession session = entry.getValue();
            
            if (session.getStartTime().isBefore(expireTime)) {
                iterator.remove();
                System.out.println("清理过期会话: " + session.getSessionId());
            }
        }
    }
    
    /**
     * 获取活跃会话数量
     */
    public int getActiveSessionCount() {
        return sessions.size();
    }
    
    /**
     * 获取指定用户的会话数量
     */
    public int getUserSessionCount(String userId) {
        return (int) sessions.values().stream()
                .filter(session -> userId.equals(session.getUserId()))
                .count();
    }
}