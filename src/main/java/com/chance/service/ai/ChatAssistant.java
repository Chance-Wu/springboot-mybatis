package com.chance.service.ai;

import dev.langchain4j.service.SystemMessage;

/**
 * @author chance
 * @date 2026/5/4 10:47
 * @since 1.0
 */
public interface ChatAssistant {

    @SystemMessage("你是企业AI助手，请用中文回答")
    String chat(String msg);
}
