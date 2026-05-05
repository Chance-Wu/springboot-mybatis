package com.chance.component.ai;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chance
 * @date 2026/5/4 10:21
 * @since 1.0
 */
@Component
public class ModelFactory {
    @Value("${ai.type}")
    private String type;

    @Value("${glm.api-key:}")
    private String glmKey;

    @Value("${glm.base-url:}")
    private String glmUrl;

    public ChatLanguageModel getModel() {

        if ("glm".equals(type)) {
            return OpenAiChatModel.builder()
                    .apiKey(glmKey)
                    .baseUrl(glmUrl)
                    .modelName("glm-4-flash")
                    .build();
        }

        throw new RuntimeException("未配置模型");
    }

}
