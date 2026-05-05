package com.chance.service.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * @author chance
 * @date 2026/5/4 10:16
 * @since 1.0
 */
public interface CodeAssistant {

    @SystemMessage("你是Java代码生成器，只返回代码")
    @UserMessage("生成代码：{{req}}")
    String generate(@V("req") String req);
}
