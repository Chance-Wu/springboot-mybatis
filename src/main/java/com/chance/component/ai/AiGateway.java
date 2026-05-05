package com.chance.component.ai;

import com.chance.service.ai.ChatAssistant;
import com.chance.service.ai.CodeAssistant;
import com.chance.service.ai.SqlAssistant;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Component;

/**
 * @author chance
 * @date 2026/5/4 10:27
 * @since 1.0
 */
@Component
public class AiGateway {

    private final CodeAssistant codeAssistant;
    private final ChatAssistant chatAssistant;
    private final SqlAssistant sqlAssistant;

    public AiGateway(ModelFactory factory) {

        ChatLanguageModel model = factory.getModel();

        this.sqlAssistant = AiServices.create(SqlAssistant.class, model);
        this.codeAssistant = AiServices.create(CodeAssistant.class, model);
        this.chatAssistant = AiServices.create(ChatAssistant.class, model);
    }

    public String chat(String msg) {
        return chatAssistant.chat(msg);
    }

    public String optimizeSql(String sql) {
        // 使用新的optimizeSql方法，需要传入schemaInfo
        // 这里简化处理，直接返回说明
        return "请使用 /smart-sql/optimize 接口进行SQL优化";
    }

    public String generateCode(String req) {
        return codeAssistant.generate(req);
    }
}
