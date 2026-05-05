package com.chance.service.ai;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

/**
 * 支持多轮对话的聊天助手
 * 使用@MemoryId实现对话记忆功能
 *
 * @author chance
 * @date 2026/5/4 16:00
 * @since 1.0
 */
public interface ConversationalAssistant {

    /**
     * 带记忆的聊天方法
     * 同一个userId的对话会被关联起来，形成上下文
     *
     * @param userId  用户ID（用于标识不同的对话会话）
     * @param message 用户消息
     * @return AI回复
     */
    @SystemMessage({
            "你是一个友好的AI助手，能够进行多轮对话。",
            "请记住之前的对话内容，保持对话的连贯性。",
            "如果用户提到之前说过的内容，要能够正确理解和回应。"
    })
    @UserMessage("{{message}}")
    String chat(@MemoryId String userId, @UserMessage String message);

    /**
     * 清除指定用户的对话记忆
     *
     * @param userId 用户ID
     */
    void clearMemory(@MemoryId String userId);
}
