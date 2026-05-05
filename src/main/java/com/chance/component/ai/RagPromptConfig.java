package com.chance.component.ai;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RAG Prompt配置
 * 支持自定义Prompt模板
 *
 * @author chance
 * @date 2026/5/4 15:30
 * @since 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "rag.prompt")
public class RagPromptConfig {

    /**
     * 系统指令模板
     */
    private String systemInstruction = "你是一个专业的智能问答助手。请根据提供的上下文信息回答用户的问题。";

    /**
     * 回答规则
     */
    private String[] answerRules = {
            "仅基于提供的上下文信息回答问题，不要使用你的训练数据",
            "如果上下文中没有足够信息回答问题，请明确说明\"根据提供的资料，我无法回答这个问题\"",
            "回答要准确、简洁、有条理",
            "如果上下文中有多个相关信息，请综合整理后回答",
            "保持回答的专业性和准确性"
    };

    /**
     * 上下文标题
     */
    private String contextTitle = "【上下文信息】";

    /**
     * 问题标题
     */
    private String questionTitle = "【用户问题】";

    /**
     * 回答标题
     */
    private String answerTitle = "【回答】";

    /**
     * 是否显示片段编号
     */
    private boolean showSegmentNumber = true;

    /**
     * 片段分隔符
     */
    private String segmentSeparator = "\n";

    /**
     * 获取格式化的回答规则
     */
    public String getFormattedRules() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < answerRules.length; i++) {
            sb.append(String.format("%d. %s\n", i + 1, answerRules[i]));
        }
        return sb.toString();
    }
}
