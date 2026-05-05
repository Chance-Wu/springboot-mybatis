package com.chance.service.ai;

import com.chance.component.ai.DatabaseSchemaInjector;
import com.chance.component.ai.ModelFactory;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * RAG + Schema 融合服务
 * 结合知识库（业务规则、SQL最佳实践）和数据库Schema，生成更智能的SQL
 *
 * @author chance
 * @date 2026/5/4 16:30
 * @since 1.0
 */
@Slf4j
@Service
public class RagSchemaFusionService {

    private final ChatLanguageModel chatModel;
    private final DatabaseSchemaInjector schemaInjector;
    private SqlAssistant sqlAssistant;

    // RAG组件
    private EmbeddingStore<TextSegment> knowledgeStore;
    private EmbeddingModel embeddingModel;

    public RagSchemaFusionService(ModelFactory modelFactory,
                                  DatabaseSchemaInjector schemaInjector) {
        this.chatModel = modelFactory.getModel();
        this.schemaInjector = schemaInjector;
    }

    /**
     * 初始化：创建AI助手并加载知识库
     */
    @PostConstruct
    public void init() {
        log.info("初始化RAG + Schema融合服务");

        // 1. 创建SqlAssistant
        sqlAssistant = AiServices.builder(SqlAssistant.class)
                .chatLanguageModel(chatModel)
                .build();

        // 2. 初始化RAG组件
        embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        knowledgeStore = new InMemoryEmbeddingStore<>();

        // 3. 加载SQL知识库
        loadSqlKnowledgeBase();

        log.info("RAG + Schema融合服务初始化完成");
    }

    /**
     * 加载SQL知识库（业务规则、最佳实践、常见模式）
     */
    private void loadSqlKnowledgeBase() {
        log.info("开始加载SQL知识库");

        // 示例知识库内容
        String[] knowledgeDocuments = {
                "【业务规则】用户表中，status=1表示激活，status=0表示禁用。查询活跃用户时应添加WHERE status = 1条件。",

                "【性能优化】对于大表查询，务必使用LIMIT限制返回行数，避免全表扫描。建议默认LIMIT 100。",

                "【JOIN最佳实践】多表关联时，确保关联字段有索引。优先使用INNER JOIN，只在需要保留左表所有记录时使用LEFT JOIN。",

                "【聚合查询】使用GROUP BY时，SELECT中的非聚合字段必须出现在GROUP BY子句中，否则会导致错误。",

                "【日期查询】查询最近N天的数据时，使用DATE_SUB(CURDATE(), INTERVAL N DAY)比使用DATEDIFF更高效。",

                "【模糊查询】LIKE '%keyword%'无法使用索引，应尽量使用前缀匹配LIKE 'keyword%'或使用全文索引。",

                "【分页优化】大数据量分页时，避免使用OFFSET，改用游标分页：WHERE id > last_id LIMIT 10。",

                "【NULL处理】使用COALESCE(field, default_value)处理NULL值，避免在计算中出现NULL结果。",

                "【子查询优化】相关子查询性能较差，应优先考虑使用JOIN替代。EXISTS比IN在大数据量时更高效。",

                "【索引使用】WHERE条件中的字段如果有函数包裹（如YEAR(create_time)），会导致索引失效。应改写为范围查询。",

                "【订单查询】订单表中，order_status: 0-待支付, 1-已支付, 2-已发货, 3-已完成, 4-已取消。",

                "【数据权限】查询用户数据时，必须添加user_id条件，确保只能查询当前用户的数据，防止数据泄露。"
        };

        // 向量化并存入向量库
        for (String doc : knowledgeDocuments) {
            TextSegment segment = TextSegment.from(doc);
            Embedding embedding = embeddingModel.embed(segment).content();
            knowledgeStore.add(embedding, segment);
        }

        log.info("SQL知识库加载完成，共 {} 条知识", knowledgeDocuments.length);
    }

    /**
     * 融合RAG和Schema生成SQL
     *
     * @param question 用户的自然语言查询
     * @return 生成的SQL语句
     */
    public String generateSqlWithFusion(String question) {
        long startTime = System.currentTimeMillis();

        log.info("========== 开始RAG融合SQL生成流程 ==========");
        log.info("收到融合SQL生成请求: {}", question);

        try {
            // 1. 从知识库检索相关业务规则
            log.info("[步骤1] 从知识库检索相关业务规则...");
            String knowledgeContext = retrieveRelevantKnowledge(question);
            log.info("检索到相关知识: {} 字符", knowledgeContext.length());
            log.debug("知识内容预览: {}", knowledgeContext.substring(0, Math.min(200, knowledgeContext.length())));

            // 2. 获取数据库Schema信息
            log.info("[步骤2] 注入Schema信息...");
            String schemaInfo = schemaInjector.injectRelevantSchemas(question);
            log.info("Schema信息: {} 字符", schemaInfo.length());
            log.debug("Schema内容预览: {}", schemaInfo.substring(0, Math.min(200, schemaInfo.length())));

            // 3. 构建融合Prompt
            log.info("[步骤3] 构建融合Prompt...");
            String systemInstruction = buildFusionSystemInstruction();
            String prompt = buildFusionPrompt(knowledgeContext, schemaInfo, question);
            log.info("Prompt构建完成，总长度: {} 字符", prompt.length());

            // 4. 生成SQL
            log.info("[步骤4] 调用AI生成SQL...");
            String sql = sqlAssistant.generateSql(systemInstruction, prompt);
            log.info("✅ AI生成SQL成功: {}", sql.substring(0, Math.min(150, sql.length())));

            // 5. 安全检查
            log.info("[步骤5] 执行SQL安全检查...");
            if (!isSqlSafe(sql)) {
                log.warn("⚠️ 生成的SQL可能不安全: {}", sql);
                log.info("========== RAG融合SQL生成失败（安全检查） ==========");
                return "生成的SQL存在安全风险，请手动检查";
            }
            log.info("✅ SQL安全检查通过");

            long endTime = System.currentTimeMillis();
            long generationTime = endTime - startTime;

            log.info("✅ RAG融合SQL生成成功！耗时: {}ms", generationTime);
            log.info("========== RAG融合SQL生成流程结束 ==========");
            return sql;

        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long generationTime = endTime - startTime;

            log.error("❌ 融合SQL生成失败！耗时: {}ms, 错误: {}", generationTime, e.getMessage(), e);
            log.info("========== RAG融合SQL生成流程异常结束 ==========");
            return "SQL生成失败: " + e.getMessage();
        }
    }

    /**
     * 检索相关的业务知识
     */
    private String retrieveRelevantKnowledge(String question) {
        log.debug("开始向量化查询: {}", question.substring(0, Math.min(50, question.length())));
        
        // 向量化查询
        Embedding queryEmbedding = embeddingModel.embed(question).content();
        log.debug("问题向量化完成");

        // 检索最相关的知识（Top 3）
        log.debug("开始检索相关知识（Top 3）...");
        List<EmbeddingMatch<TextSegment>> matches =
                knowledgeStore.findRelevant(queryEmbedding, 3);

        if (matches.isEmpty()) {
            log.info("未检索到相关知识");
            return "暂无相关业务规则";
        }

        log.info("检索到 {} 条相关知识", matches.size());

        // 格式化知识
        StringBuilder knowledge = new StringBuilder();
        knowledge.append("【相关业务规则和最佳实践】\n\n");

        int matchedCount = 0;
        for (int i = 0; i < matches.size(); i++) {
            TextSegment segment = matches.get(i).embedded();
            double score = matches.get(i).score();

            // 只显示相似度高于0.5的知识
            if (score >= 0.5) {
                matchedCount++;
                knowledge.append(String.format("%d. %s (相关度: %.2f)\n",
                        i + 1, segment.text(), score));
                log.debug("匹配知识 #{}: 相关度={:.2f}, 内容={}",
                        i + 1, score, segment.text().substring(0, Math.min(80, segment.text().length())));
            }
        }

        log.info("过滤后保留 {} 条高相关度知识（阈值>=0.5）", matchedCount);
        return knowledge.toString();
    }

    /**
     * 构建融合版的系统指令
     */
    private String buildFusionSystemInstruction() {
        return "你是一个资深的MySQL数据库专家和SQL架构师。\n" +
                "\n" +
                "【核心能力】\n" +
                "1. 精通数据库设计和SQL优化\n" +
                "2. 熟悉业务规则和最佳实践\n" +
                "3. 能够结合Schema和业务上下文生成高质量SQL\n" +
                "\n" +
                "【重要原则】\n" +
                "1. 严格遵循提供的业务规则\n" +
                "2. 优先考虑查询性能和安全性\n" +
                "3. 确保SQL符合MySQL规范\n" +
                "4. 应用最佳实践和优化技巧\n" +
                "\n" +
                "【输出要求】\n" +
                "- 只返回纯SQL语句\n" +
                "- 不要任何解释或注释\n" +
                "- SQL必须是可执行的完整语句";
    }

    /**
     * 构建融合Prompt（包含知识、Schema和问题）
     */
    private String buildFusionPrompt(String knowledgeContext,
                                     String schemaInfo,
                                     String question) {
        StringBuilder prompt = new StringBuilder();

        // 1. 业务知识
        prompt.append(knowledgeContext);
        prompt.append("\n\n");

        // 2. 数据库结构
        prompt.append("【数据库结构信息】\n");
        prompt.append(schemaInfo);
        prompt.append("\n\n");

        // 3. 用户需求
        prompt.append("【用户需求】\n");
        prompt.append(question);
        prompt.append("\n\n");

        // 4. 任务说明
        prompt.append("【任务】\n");
        prompt.append("请根据上述业务知识、数据库结构和用户需求，");
        prompt.append("生成一条准确、高效、符合业务规则的MySQL查询语句。\n\n");

        // 5. 思考步骤
        prompt.append("【思考步骤】\n");
        prompt.append("1. 阅读并理解相关业务规则\n");
        prompt.append("2. 分析用户需求，确定查询目标\n");
        prompt.append("3. 查看Schema，找到相关的表和字段\n");
        prompt.append("4. 应用业务规则（如状态值、权限控制等）\n");
        prompt.append("5. 考虑性能优化（索引、JOIN策略等）\n");
        prompt.append("6. 生成最终的SQL语句\n\n");

        // 6. 注意事项
        prompt.append("【特别注意】\n");
        prompt.append("- 如果知识库中有相关的业务规则，必须严格遵守\n");
        prompt.append("- 注意数据权限和安全限制\n");
        prompt.append("- 优先考虑查询性能\n");
        prompt.append("- 使用规范的SQL格式\n\n");

        prompt.append("【生成的SQL】");

        return prompt.toString();
    }

    /**
     * SQL安全性检查
     */
    private boolean isSqlSafe(String sql) {
        if (sql == null || sql.isEmpty()) {
            return false;
        }

        // 清理SQL：去除Markdown代码块标记、前后空白和换行符
        String cleanedSql = sql.trim()
                .replaceAll("^```sql\\s*", "")  // 去除开头的 ```sql
                .replaceAll("^```\\s*", "")      // 去除开头的 ```
                .replaceAll("\\s*```$", "")      // 去除结尾的 ```
                .trim();

        String lowerSql = cleanedSql.toLowerCase();

        // 只允许SELECT语句（忽略大小写）
        if (!lowerSql.startsWith("select")) {
            log.warn("不允许非SELECT语句: {}", sql);
            return false;
        }

        // 检查危险关键字（使用单词边界匹配，避免子字符串误判）
        String[] dangerousKeywords = {
                "DROP", "DELETE", "UPDATE", "INSERT",
                "ALTER", "CREATE", "TRUNCATE"
        };

        for (String keyword : dangerousKeywords) {
            // 使用单词边界匹配，确保匹配完整的关键字
            // 例如：INTERVAL 不会匹配 CREATE
            Pattern pattern = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(cleanedSql).find()) {
                log.warn("检测到危险关键字: {}", keyword);
                return false;
            }
        }

        return true;
    }

    /**
     * 添加自定义业务知识
     *
     * @param knowledge 业务知识文本
     */
    public void addCustomKnowledge(String knowledge) {
        log.info("添加自定义业务知识");

        TextSegment segment = TextSegment.from(knowledge);
        Embedding embedding = embeddingModel.embed(segment).content();
        knowledgeStore.add(embedding, segment);

        log.info("知识添加成功");
    }

    /**
     * 批量添加业务知识
     *
     * @param knowledges 知识列表
     */
    public void addBatchKnowledge(List<String> knowledges) {
        log.info("批量添加 {} 条业务知识", knowledges.size());

        for (String knowledge : knowledges) {
            addCustomKnowledge(knowledge);
        }

        log.info("批量添加完成");
    }

    /**
     * 获取知识库统计信息
     */
    public Map<String, Object> getKnowledgeStats() {
        Map<String, Object> stats = new HashMap<>();
        // InMemoryEmbeddingStore没有直接获取大小的方法
        // 这里返回一个占位值
        stats.put("knowledgeCount", "动态加载");
        stats.put("embeddingModel", "All-MiniLM-L6-v2");
        return stats;
    }
}
