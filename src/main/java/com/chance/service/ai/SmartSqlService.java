package com.chance.service.ai;

import com.chance.component.ai.DatabaseSchemaExtractor;
import com.chance.component.ai.DatabaseSchemaInjector;
import com.chance.component.ai.ModelFactory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 智能SQL生成服务
 * 结合RAG和数据库Schema，实现自然语言到SQL的转换
 *
 * @author chance
 * @date 2026/5/4 16:10
 * @since 1.0
 */
@Slf4j
@Service
public class SmartSqlService {

    private final ChatLanguageModel chatModel;
    private final DatabaseSchemaExtractor schemaExtractor;
    private final DatabaseSchemaInjector schemaInjector;
    private SqlAssistant sqlAssistant;

    public SmartSqlService(ModelFactory modelFactory,
                           DatabaseSchemaExtractor schemaExtractor,
                           DatabaseSchemaInjector schemaInjector) {
        this.chatModel = modelFactory.getModel();
        this.schemaExtractor = schemaExtractor;
        this.schemaInjector = schemaInjector;
    }

    /**
     * 初始化：创建SqlAssistant实例
     */
    @PostConstruct
    public void init() {
        log.info("初始化智能SQL生成服务");

        sqlAssistant = AiServices.builder(SqlAssistant.class)
                .chatLanguageModel(chatModel)
                .build();

        log.info("SqlAssistant创建完成");
    }

    /**
     * 根据自然语言生成SQL（使用升级的Prompt）
     *
     * @param question 用户的自然语言查询需求
     * @return 生成的SQL语句
     */
    public String generateSql(String question) {
        log.info("收到SQL生成请求: {}", question);

        try {
            // 1. 智能注入相关表的Schema信息
            String schemaInfo = schemaInjector.injectRelevantSchemas(question);
            log.debug("Schema信息长度: {} 字符", schemaInfo.length());

            // 2. 构建优化的Prompt
            String systemInstruction = buildSystemInstruction();
            String prompt = buildGeneratePrompt(schemaInfo, question);

            // 3. 使用AI生成SQL
            String sql = sqlAssistant.generateSql(systemInstruction, prompt);

            log.info("生成SQL: {}", sql);

            // 4. 验证SQL安全性（简单检查）
            if (!isSqlSafe(sql)) {
                log.warn("生成的SQL可能不安全: {}", sql);
                return "生成的SQL存在安全风险，请手动检查";
            }

            return sql;

        } catch (Exception e) {
            log.error("SQL生成失败", e);
            return "SQL生成失败: " + e.getMessage();
        }
    }

    /**
     * 针对特定表生成SQL
     *
     * @param tableName 表名
     * @param question  查询需求
     * @return 生成的SQL
     */
    public String generateSqlForTable(String tableName, String question) {
        log.info("为表 {} 生成SQL: {}", tableName, question);

        try {
            // 1. 注入指定表的Schema
            String schemaInfo = schemaInjector.injectTableSchema(tableName);

            // 2. 构建Prompt
            String systemInstruction = buildSystemInstruction();
            String prompt = buildGeneratePrompt(schemaInfo, question);

            // 3. 生成SQL
            String sql = sqlAssistant.generateSql(systemInstruction, prompt);

            log.info("生成SQL: {}", sql);
            return sql;

        } catch (Exception e) {
            log.error("SQL生成失败", e);
            return "SQL生成失败: " + e.getMessage();
        }
    }

    /**
     * 解释SQL语句
     *
     * @param sql SQL语句
     * @return 解释说明
     */
    public String explainSql(String sql) {
        log.info("解释SQL: {}", sql);

        try {
            String systemInstruction = "你是一个SQL教学专家，擅长用通俗易懂的语言解释SQL语句。\n" +
                    "请用简洁的中文解释SQL的作用，控制在200字以内。";
            String prompt = "请解释以下SQL语句：\n" + sql;
            return sqlAssistant.explainSql(systemInstruction, prompt);
        } catch (Exception e) {
            log.error("SQL解释失败", e);
            return "SQL解释失败: " + e.getMessage();
        }
    }

    /**
     * 优化SQL语句
     *
     * @param sql 原始SQL
     * @return 优化后的SQL
     */
    public String optimizeSql(String sql) {
        log.info("优化SQL: {}", sql);

        try {
            // 使用精简版Schema进行优化
            String schemaInfo = schemaInjector.injectLiteSchema();
            String systemInstruction = "你是一个MySQL性能优化专家，专注于SQL查询优化。\n" +
                    "请优化SQL以提高性能，只返回优化后的SQL，不要解释。";
            String prompt = "【数据库结构】\n" + schemaInfo + "\n\n【原始SQL】\n" + sql + "\n\n【优化后的SQL】";
            return sqlAssistant.optimizeSql(systemInstruction, prompt);
        } catch (Exception e) {
            log.error("SQL优化失败", e);
            return "SQL优化失败: " + e.getMessage();
        }
    }

    /**
     * 执行生成的SQL并返回结果
     *
     * @param question 自然语言查询
     * @return 查询结果
     */
    public Map<String, Object> executeGeneratedSql(String question) {
        log.info("执行智能SQL查询: {}", question);

        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 生成SQL
            String sql = generateSql(question);

            if (sql.startsWith("SQL生成失败") || sql.startsWith("生成的SQL存在安全风险")) {
                result.put("success", false);
                result.put("error", sql);
                return result;
            }

            result.put("sql", sql);
            result.put("success", true);
            result.put("message", "SQL生成成功，请确认后执行");

        } catch (Exception e) {
            log.error("查询执行失败", e);
            result.put("success", false);
            result.put("error", "查询执行失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * SQL安全性检查（基础版本）
     */
    private boolean isSqlSafe(String sql) {
        if (sql == null || sql.isEmpty()) {
            return false;
        }

        String lowerSql = sql.toLowerCase().trim();

        // 只允许SELECT语句（防止数据修改）
        if (!lowerSql.startsWith("select")) {
            log.warn("不允许非SELECT语句: {}", sql);
            return false;
        }

        // 检查危险关键字
        String[] dangerousKeywords = {
                "drop ", "delete ", "update ", "insert ",
                "alter ", "create ", "truncate ",
                "exec ", "execute ", "xp_", "sp_"
        };

        for (String keyword : dangerousKeywords) {
            if (lowerSql.contains(keyword)) {
                log.warn("检测到危险关键字: {}", keyword);
                return false;
            }
        }

        return true;
    }

    /**
     * 构建系统指令（升级的Prompt）
     */
    private String buildSystemInstruction() {
        return "你是一个资深的MySQL数据库专家和SQL开发顾问。\n" +
                "\n" +
                "【核心原则】\n" +
                "1. 准确性优先：确保SQL语法完全正确，字段名、表名严格匹配Schema\n" +
                "2. 性能优化：优先考虑查询性能，合理使用索引\n" +
                "3. 最佳实践：遵循SQL编写规范，保持代码可读性\n" +
                "4. 安全第一：防止SQL注入\n" +
                "\n" +
                "【SQL编写规范】\n" +
                "1. 关键字使用大写（SELECT, FROM, WHERE等）\n" +
                "2. 表名和字段名使用小写\n" +
                "3. 使用明确的字段列表，避免SELECT *\n" +
                "4. 合理使用别名提高可读性\n" +
                "\n" +
                "【输出要求】\n" +
                "- 只返回纯SQL语句，不要任何解释或注释\n" +
                "- SQL必须是可执行的完整语句\n" +
                "- 如果无法生成SQL，返回：无法生成SQL";
    }

    /**
     * 构建生成SQL的Prompt
     */
    private String buildGeneratePrompt(String schemaInfo, String question) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("【数据库结构信息】\n");
        prompt.append(schemaInfo);
        prompt.append("\n\n");

        prompt.append("【用户需求】\n");
        prompt.append(question);
        prompt.append("\n\n");

        prompt.append("【任务】\n");
        prompt.append("请根据上述数据库结构和用户需求，生成一条准确、高效的MySQL查询语句。\n\n");

        prompt.append("【思考步骤】\n");
        prompt.append("1. 分析用户需求，确定需要查询的数据\n");
        prompt.append("2. 查看Schema，找到相关的表和字段\n");
        prompt.append("3. 确定是否需要JOIN、聚合、排序等操作\n");
        prompt.append("4. 考虑性能优化，选择合适的写法\n\n");

        prompt.append("【参考示例】\n");
        prompt.append("- 需求: 查询所有用户 -> SELECT id, username FROM user\n");
        prompt.append("- 需求: 查询年龄大于18的用户 -> SELECT id, username FROM user WHERE age > 18\n");
        prompt.append("- 需求: 统计每个年龄的用户数量 -> SELECT age, COUNT(*) as count FROM user GROUP BY age\n\n");

        prompt.append("【生成的SQL】");

        return prompt.toString();
    }

    /**
     * 获取数据库Schema信息（用于调试）
     */
    public String getSchemaInfo() {
        return schemaInjector.injectAllSchemas();
    }

    /**
     * 清除Schema缓存
     */
    public void clearSchemaCache() {
        schemaInjector.clearCache();
    }

    /**
     * 获取缓存统计
     */
    public Map<String, Object> getSchemaCacheStats() {
        return schemaInjector.getCacheStats();
    }
}
