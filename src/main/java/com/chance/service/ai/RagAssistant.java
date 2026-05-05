package com.chance.service.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * RAG知识库问答助手
 * 使用AiServices封装，提供简洁的接口调用方式
 *
 * @author chance
 * @date 2026/5/4 15:30
 * @since 1.0
 */
public interface RagAssistant {

    /**
     * 基于知识库回答问题
     *
     * @param question 用户问题
     * @return AI生成的回答
     */
    @SystemMessage({
            "你是一个专业的智能问答助手。请根据提供的上下文信息回答用户的问题。",
            "",
            "【回答规则】",
            "1. 仅基于提供的上下文信息回答问题，不要使用你的训练数据",
            "2. 如果上下文中没有足够信息回答问题，请明确说明\"根据提供的资料，我无法回答这个问题\"",
            "3. 回答要准确、简洁、有条理",
            "4. 如果上下文中有多个相关信息，请综合整理后回答",
            "5. 保持回答的专业性和准确性"
    })
    @UserMessage("【上下文信息】\n{{context}}\n\n【用户问题】\n{{question}}\n\n【回答】")
    String answer(@V("context") String context, @V("question") String question);

    /**
     * 简化的问答方法（由Service层处理检索逻辑）
     *
     * @param question 用户问题
     * @return AI生成的回答
     */
    @SystemMessage({
            "你是一个专业的智能问答助手，基于企业知识库回答问题。",
            "如果知识库中没有相关信息，请礼貌地告知用户。"
    })
    @UserMessage("{{question}}")
    String ask(String question);
}
