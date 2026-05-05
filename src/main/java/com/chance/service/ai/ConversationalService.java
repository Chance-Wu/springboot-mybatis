package com.chance.service.ai;

import com.chance.component.ai.ModelFactory;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多轮对话服务
 * 使用ChatMemory实现对话记忆功能
 *
 * @author chance
 * @date 2026/5/4 16:00
 * @since 1.0
 */
@Slf4j
@Service
public class ConversationalService {

    private final ChatLanguageModel chatModel;
    private ConversationalAssistant conversationalAssistant;

    // 存储每个用户的对话记忆
    private final Map<String, ChatMemory> userMemories = new ConcurrentHashMap<>();

    // 默认记忆窗口大小（保留最近的消息数量）
    private static final int DEFAULT_MAX_MESSAGES = 20;

    public ConversationalService(ModelFactory modelFactory) {
        this.chatModel = modelFactory.getModel();
    }

    /**
     * 初始化：创建ConversationalAssistant实例
     */
    @PostConstruct
    public void init() {
        log.info("初始化多轮对话服务");

        // 创建带记忆的AI助手
        conversationalAssistant = AiServices.builder(ConversationalAssistant.class)
                .chatLanguageModel(chatModel)
                .chatMemoryProvider(memoryId -> {
                    // 为每个用户创建独立的对话记忆
                    String userId = (String) memoryId;
                    return getOrCreateMemory(userId);
                })
                .build();

        log.info("ConversationalAssistant创建完成，支持多轮对话");
    }

    /**
     * 发送消息并获取回复（带记忆）
     *
     * @param userId  用户ID
     * @param message 用户消息
     * @return AI回复
     */
    public String chat(String userId, String message) {
        log.debug("用户 {} 发送消息: {}", userId, message);

        try {
            String response = conversationalAssistant.chat(userId, message);
            log.debug("AI回复用户 {}: {}", userId, response);
            return response;
        } catch (Exception e) {
            log.error("对话处理失败", e);
            return "抱歉，我遇到了一些问题，请稍后重试。";
        }
    }

    /**
     * 清除指定用户的对话记忆
     *
     * @param userId 用户ID
     */
    public void clearMemory(String userId) {
        log.info("清除用户 {} 的对话记忆", userId);
        userMemories.remove(userId);
    }

    /**
     * 获取或创建用户的对话记忆
     *
     * @param userId 用户ID
     * @return ChatMemory实例
     */
    private ChatMemory getOrCreateMemory(String userId) {
        return userMemories.computeIfAbsent(userId, id -> {
            log.info("为用户 {} 创建新的对话记忆", id);
            return MessageWindowChatMemory.builder()
                    .maxMessages(DEFAULT_MAX_MESSAGES)
                    .build();
        });
    }

    /**
     * 获取用户的对话历史
     *
     * @param userId 用户ID
     * @return 对话历史列表
     */
    public java.util.List<ChatMessage> getConversationHistory(String userId) {
        ChatMemory memory = userMemories.get(userId);
        if (memory == null) {
            return java.util.Collections.emptyList();
        }
        return memory.messages();
    }

    /**
     * 获取所有活跃会话数
     *
     * @return 会话数量
     */
    public int getActiveSessionCount() {
        return userMemories.size();
    }

    /**
     * 手动添加消息到记忆（用于导入历史对话等场景）
     *
     * @param userId      用户ID
     * @param userMessage 用户消息
     * @param aiMessage   AI回复
     */
    public void addMessageToMemory(String userId, String userMessage, String aiMessage) {
        ChatMemory memory = getOrCreateMemory(userId);
        memory.add(UserMessage.from(userMessage));
        memory.add(AiMessage.from(aiMessage));
        log.debug("为用户 {} 添加历史消息到记忆", userId);
    }
}
