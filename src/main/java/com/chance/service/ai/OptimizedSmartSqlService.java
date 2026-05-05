package com.chance.service.ai;

import com.chance.component.ai.DatabaseSchemaInjector;
import com.chance.component.ai.ModelFactory;
import com.chance.component.ai.SqlSecurity;
import com.chance.component.ai.StructuredSqlResult;
import com.chance.component.ai.StructuredSqlResultBuilder;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 优化的SQL生成服务（增强版）
 * 包含缓存、重试、质量评估、性能监控等功能
 *
 * @author chance
 * @date 2026/5/4 16:15
 * @since 2.0
 */
@Slf4j
@Service
public class OptimizedSmartSqlService {

    private final ChatLanguageModel chatModel;
    private final DatabaseSchemaInjector schemaInjector;
    private final SqlSecurity securityController;
    private final StructuredSqlResultBuilder resultBuilder;
    private SqlAssistant sqlAssistant;

    // SQL缓存（问题 -> SQL）
    private final Map<String, CachedSql> sqlCache = new ConcurrentHashMap<>();

    // 缓存过期时间（毫秒）- 默认30分钟
    private static final long CACHE_EXPIRE_TIME = 30 * 60 * 1000;

    // 最大重试次数
    private static final int MAX_RETRY_COUNT = 2;

    // 性能统计
    private long totalRequests = 0;
    private long totalSuccess = 0;
    private long totalFailures = 0;
    private long totalCacheHits = 0;
    private long totalTimeMs = 0;

    public OptimizedSmartSqlService(ModelFactory modelFactory,
                                    DatabaseSchemaInjector schemaInjector,
                                    SqlSecurity securityController,
                                    StructuredSqlResultBuilder resultBuilder) {
        this.chatModel = modelFactory.getModel();
        this.schemaInjector = schemaInjector;
        this.securityController = securityController;
        this.resultBuilder = resultBuilder;
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        log.info("初始化优化的SQL生成服务");

        sqlAssistant = AiServices.builder(SqlAssistant.class)
                .chatLanguageModel(chatModel)
                .build();

        log.info("优化的SQL生成服务初始化完成");
    }

    /**
     * 生成SQL（带缓存和重试）
     *
     * @param question 用户的自然语言查询需求
     * @return SQL生成结果
     */
    public SqlGenerationResult generateSql(String question) {
        long startTime = System.currentTimeMillis();
        totalRequests++;

        log.info("========== 开始SQL生成流程 ==========");
        log.info("收到SQL生成请求 [{}]: {}", totalRequests, question);

        SqlGenerationResult result = new SqlGenerationResult();
        result.setQuestion(question);

        try {
            // 1. 检查缓存
            log.info("[步骤1] 检查缓存...");
            CachedSql cached = checkCache(question);
            if (cached != null) {
                totalCacheHits++;
                result.setSuccess(true);  // ✅ 设置成功标志
                result.setSql(cached.getSql());
                result.setFromCache(true);
                result.setGenerationTimeMs(0L);

                log.info("✅ 缓存命中，直接返回SQL: {}", cached.getSql().substring(0, Math.min(100, cached.getSql().length())));
                log.info("========== SQL生成完成（缓存） ==========");
                return result;
            }
            log.info("❌ 缓存未命中，继续生成SQL");

            // 2. 智能注入相关表的Schema信息
            log.info("[步骤2] 注入Schema信息...");
            String schemaInfo = schemaInjector.injectRelevantSchemas(question);
            log.info("Schema信息长度: {} 字符", schemaInfo.length());
            log.debug("Schema内容预览: {}", schemaInfo.substring(0, Math.min(200, schemaInfo.length())));

            // 3. 构建优化的Prompt
            log.info("[步骤3] 构建Prompt...");
            String systemInstruction = buildSystemInstruction();
            String prompt = buildGeneratePrompt(schemaInfo, question);
            log.info("Prompt构建完成，总长度: {} 字符", prompt.length());

            // 4. 使用AI生成SQL（带重试）
            log.info("[步骤4] 调用AI生成SQL（最多重试{}次）...", MAX_RETRY_COUNT);
            String sql = generateWithRetry(systemInstruction, prompt, MAX_RETRY_COUNT);
            log.info("✅ AI生成SQL成功: {}", sql.substring(0, Math.min(150, sql.length())));

            // 5. 验证SQL安全性（增强版）
            log.info("[步骤5] 执行SQL安全检查...");
            SqlSecurity.SecurityCheckResult securityCheck =
                    securityController.checkSqlSecurity(sql);

            if (!securityCheck.isSafe()) {
                log.warn("⚠️ SQL安全检查失败: {}", securityCheck.getMessage());
                log.info("========== SQL生成失败（安全检查） ==========");
                result.setSuccess(false);
                result.setError("SQL安全检查失败: " + securityCheck.getMessage());
                totalFailures++;
                return result;
            }
            log.info("✅ SQL安全检查通过");

            // 6. 自动添加LIMIT限制
            log.info("[步骤6] 添加默认LIMIT限制...");
            String limitedSql = securityController.addDefaultLimit(sql);
            log.info("添加LIMIT后SQL: {}", limitedSql.substring(0, Math.min(150, limitedSql.length())));

            // 7. 质量评估
            log.info("[步骤7] 评估SQL质量...");
            QualityScore quality = evaluateQuality(limitedSql, schemaInfo, question);
            log.info("质量评分: {}分 (等级: {})", quality.getOverallScore(), quality.getLevel());
            log.debug("详细评分 - 格式:{}, 实践:{}, 性能:{}, 安全:{}",
                    quality.getFormatScore(), quality.getPracticeScore(),
                    quality.getPerformanceScore(), quality.getSecurityScore());

            // 8. 缓存结果
            log.info("[步骤8] 缓存SQL结果...");
            cacheResult(question, limitedSql);

            // 9. 构建成功结果
            long endTime = System.currentTimeMillis();
            long generationTime = endTime - startTime;

            result.setSuccess(true);
            result.setSql(limitedSql);
            result.setFromCache(false);
            result.setGenerationTimeMs(generationTime);
            result.setQualityScore(quality);

            totalSuccess++;
            totalTimeMs += generationTime;

            log.info("✅ SQL生成成功！耗时: {}ms, 质量评分: {}/100 ({})",
                    generationTime, quality.getOverallScore(), quality.getLevel());
            log.info("========== SQL生成流程结束 ==========");
            return result;

        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long generationTime = endTime - startTime;

            log.error("❌ SQL生成失败！耗时: {}ms, 错误: {}", generationTime, e.getMessage(), e);
            log.info("========== SQL生成流程异常结束 ==========");

            result.setSuccess(false);
            result.setError("SQL生成失败: " + e.getMessage());
            result.setGenerationTimeMs(generationTime);

            totalFailures++;
            return result;
        }
    }

    /**
     * 生成结构化SQL结果（增强版）
     *
     * @param question 用户的自然语言查询需求
     * @return 结构化的SQL生成结果
     */
    public StructuredSqlResult generateStructuredSql(String question) {
        long startTime = System.currentTimeMillis();
        totalRequests++;

        log.info("========== 开始结构化SQL生成流程 ==========");
        log.info("收到结构化SQL生成请求 [{}]: {}", totalRequests, question);

        try {
            // 1. 检查缓存
            log.info("[步骤1] 检查缓存...");
            CachedSql cached = checkCache(question);
            if (cached != null) {
                totalCacheHits++;
                long endTime = System.currentTimeMillis();

                log.info("✅ 缓存命中，返回结构化结果");
                log.info("========== 结构化SQL生成完成（缓存） ==========");
                
                // 构建结构化结果（缓存命中）
                StructuredSqlResult result = resultBuilder.buildCompleteResult(
                        cached.getSql(),
                        question,
                        true,
                        0L,
                        SqlSecurity.SecurityCheckResult.safe(),
                        null
                );

                return result;
            }
            log.info("❌ 缓存未命中，继续生成SQL");

            // 2. 智能注入相关表的Schema信息
            log.info("[步骤2] 注入Schema信息...");
            String schemaInfo = schemaInjector.injectRelevantSchemas(question);
            log.info("Schema信息长度: {} 字符", schemaInfo.length());
            log.debug("Schema内容预览: {}", schemaInfo.substring(0, Math.min(200, schemaInfo.length())));

            // 3. 构建优化的Prompt
            log.info("[步骤3] 构建Prompt...");
            String systemInstruction = buildSystemInstruction();
            String prompt = buildGeneratePrompt(schemaInfo, question);
            log.info("Prompt构建完成，总长度: {} 字符", prompt.length());

            // 4. 使用AI生成SQL（带重试）
            log.info("[步骤4] 调用AI生成SQL（最多重试{}次）...", MAX_RETRY_COUNT);
            String sql = generateWithRetry(systemInstruction, prompt, MAX_RETRY_COUNT);
            log.info("✅ AI生成SQL成功: {}", sql.substring(0, Math.min(150, sql.length())));

            // 5. 验证SQL安全性（增强版）
            log.info("[步骤5] 执行SQL安全检查...");
            SqlSecurity.SecurityCheckResult securityCheck =
                    securityController.checkSqlSecurity(sql);

            if (!securityCheck.isSafe()) {
                log.warn("⚠️ SQL安全检查失败: {}", securityCheck.getMessage());
                log.info("========== 结构化SQL生成失败（安全检查） ==========");
                totalFailures++;
                return StructuredSqlResult.failure(
                        "SQL安全检查失败: " + securityCheck.getMessage(),
                        question
                );
            }
            log.info("✅ SQL安全检查通过");

            // 6. 自动添加LIMIT限制
            log.info("[步骤6] 添加默认LIMIT限制...");
            String limitedSql = securityController.addDefaultLimit(sql);
            log.info("添加LIMIT后SQL: {}", limitedSql.substring(0, Math.min(150, limitedSql.length())));

            // 7. 缓存结果
            log.info("[步骤7] 缓存SQL结果...");
            cacheResult(question, limitedSql);

            // 8. 构建结构化结果
            long endTime = System.currentTimeMillis();
            long generationTime = endTime - startTime;

            log.info("[步骤8] 构建结构化结果...");
            StructuredSqlResult result = resultBuilder.buildCompleteResult(
                    limitedSql,
                    question,
                    false,
                    generationTime,
                    securityCheck,
                    schemaInfo
            );

            totalSuccess++;
            totalTimeMs += generationTime;

            log.info("✅ 结构化SQL生成成功！耗时: {}ms", generationTime);
            log.info("========== 结构化SQL生成流程结束 ==========");
            return result;

        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long generationTime = endTime - startTime;

            log.error("❌ 结构化SQL生成失败！耗时: {}ms, 错误: {}", generationTime, e.getMessage(), e);
            log.info("========== 结构化SQL生成流程异常结束 ==========");

            totalFailures++;
            return StructuredSqlResult.failure(
                    "SQL生成失败: " + e.getMessage(),
                    question
            );
        }
    }

    /**
     * 带重试的SQL生成
     */
    private String generateWithRetry(String systemInstruction, String prompt, int maxRetries) {
        Exception lastException = null;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                log.info("🔄 第 {}/{} 次尝试生成SQL...", attempt, maxRetries);
                String sql = sqlAssistant.generateSql(systemInstruction, prompt);

                // 记录AI返回的原始内容（前200字符）
                String preview = sql != null ? sql.substring(0, Math.min(200, sql.length())) : "null";
                log.debug("AI返回原始内容: {}", preview);

                // 验证生成的SQL是否有效
                if (isValidSql(sql)) {
                    log.info("✅ 第 {} 次生成成功，SQL有效", attempt);
                    return sql;
                }

                log.warn("⚠️ 第 {} 次生成的SQL无效，准备重试。SQL预览: {}", attempt, preview);

            } catch (Exception e) {
                lastException = e;
                log.error("❌ 第 {} 次生成失败: {}", attempt, e.getMessage());
                log.debug("异常详情:", e);
            }

            // 重试前等待（指数退避）
            if (attempt < maxRetries) {
                try {
                    long waitTime = 1000 * attempt;  // 1s, 2s, 4s...
                    log.info("⏳ 等待 {}ms 后进行第 {} 次重试...", waitTime, attempt + 1);
                    Thread.sleep(waitTime);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    log.error("重试被中断");
                    throw new RuntimeException("重试被中断", ie);
                }
            }
        }

        log.error("❌ 经过{}次重试后仍然失败", maxRetries);
        throw new RuntimeException("经过" + maxRetries + "次重试后仍然失败: " +
                (lastException != null ? lastException.getMessage() : "未知错误"), lastException);
    }

    /**
     * 检查缓存
     */
    private CachedSql checkCache(String question) {
        String cacheKey = normalizeQuestion(question);
        CachedSql cached = sqlCache.get(cacheKey);

        if (cached != null && !cached.isExpired()) {
            log.debug("缓存命中: {}", cacheKey);
            return cached;
        }

        if (cached != null) {
            log.debug("缓存已过期，移除: {}", cacheKey);
            sqlCache.remove(cacheKey);
        }

        return null;
    }

    /**
     * 缓存结果
     */
    private void cacheResult(String question, String sql) {
        String cacheKey = normalizeQuestion(question);
        CachedSql cached = new CachedSql(sql);
        sqlCache.put(cacheKey, cached);

        log.debug("缓存SQL: {}, 当前缓存大小: {}", cacheKey, sqlCache.size());
    }

    /**
     * 标准化问题（用于缓存key）
     */
    private String normalizeQuestion(String question) {
        // 转小写、去除多余空格
        return question.toLowerCase().trim().replaceAll("\\s+", " ");
    }

    /**
     * 验证SQL是否有效
     */
    private boolean isValidSql(String sql) {
        if (sql == null || sql.isEmpty()) {
            log.debug("❌ SQL验证失败: SQL为空");
            return false;
        }

        // 清理Markdown代码块标记
        String cleanedSql = sql.trim()
                .replaceAll("^```sql\\s*", "")  // 去除开头的 ```sql
                .replaceAll("^```\\s*", "")      // 去除开头的 ```
                .replaceAll("\\s*```$", "")      // 去除结尾的 ```
                .trim();

        // 检查是否是错误消息
        if (cleanedSql.contains("无法生成SQL") || cleanedSql.contains("失败")) {
            log.debug("❌ SQL验证失败: 包含错误消息 - {}", cleanedSql.substring(0, Math.min(100, cleanedSql.length())));
            return false;
        }

        // 检查是否以SELECT开头（允许前后有空格）
        boolean isValid = cleanedSql.toLowerCase().startsWith("select");

        if (isValid) {
            log.debug("✅ SQL验证通过: {}", cleanedSql.substring(0, Math.min(100, cleanedSql.length())));
        } else {
            log.debug("❌ SQL验证失败: 不以SELECT开头 - {}", cleanedSql.substring(0, Math.min(100, cleanedSql.length())));
        }

        return isValid;
    }

    /**
     * 评估SQL质量
     */
    private QualityScore evaluateQuality(String sql, String schemaInfo, String question) {
        QualityScore score = new QualityScore();

        // 1. 格式规范（20分）
        int formatScore = evaluateFormat(sql);
        score.setFormatScore(formatScore);

        // 2. 最佳实践（30分）
        int practiceScore = evaluateBestPractices(sql);
        score.setPracticeScore(practiceScore);

        // 3. 性能考虑（30分）
        int performanceScore = evaluatePerformance(sql, schemaInfo);
        score.setPerformanceScore(performanceScore);

        // 4. 安全性（20分）
        int securityScore = evaluateSecurity(sql);
        score.setSecurityScore(securityScore);

        // 计算总分
        int overallScore = formatScore + practiceScore + performanceScore + securityScore;
        score.setOverallScore(overallScore);

        // 评级
        if (overallScore >= 90) {
            score.setLevel("优秀");
        } else if (overallScore >= 75) {
            score.setLevel("良好");
        } else if (overallScore >= 60) {
            score.setLevel("一般");
        } else {
            score.setLevel("需改进");
        }

        return score;
    }

    /**
     * 评估格式规范
     */
    private int evaluateFormat(String sql) {
        int score = 20;

        // 关键字大写（简化检查）
        String lowerSql = sql.toLowerCase();
        if (lowerSql.contains("select") || lowerSql.contains("from") ||
                lowerSql.contains("where") || lowerSql.contains("join")) {
            // 检查是否有小写关键字
            if (!sql.contains("SELECT") && !sql.contains("FROM")) {
                score -= 5;  // 全部小写，扣5分
            }
        }

        // 避免SELECT *
        if (lowerSql.contains("select *")) {
            score -= 10;
        }

        // 有合理的换行和缩进
        if (sql.contains("\n") && sql.trim().length() > 50) {
            score -= 0;  // 有格式化，不扣分
        } else if (sql.trim().length() > 100) {
            score -= 5;  // 长SQL但没有格式化，扣5分
        }

        return Math.max(0, score);
    }

    /**
     * 评估最佳实践
     */
    private int evaluateBestPractices(String sql) {
        int score = 30;

        // 使用别名
        if (sql.toLowerCase().contains(" join ") && !sql.toLowerCase().contains(" as ")) {
            score -= 5;  // JOIN但没有别名，扣5分
        }

        // 明确的字段列表
        if (sql.toLowerCase().matches(".*select\\s+\\w+\\s+from.*")) {
            score -= 0;  // 有明确字段，不扣分
        }

        // 避免在索引列上使用函数（简单检查）
        String lowerSql = sql.toLowerCase();
        if (lowerSql.contains("year(") || lowerSql.contains("month(") ||
                lowerSql.contains("date(") || lowerSql.contains("upper(")) {
            score -= 10;  // 可能影响索引使用
        }

        return Math.max(0, score);
    }

    /**
     * 评估性能
     */
    private int evaluatePerformance(String sql, String schemaInfo) {
        int score = 30;

        String lowerSql = sql.toLowerCase();

        // 有LIMIT
        if (!lowerSql.contains("limit")) {
            score -= 10;  // 没有LIMIT，可能返回大量数据
        }

        // 有WHERE条件
        if (!lowerSql.contains("where")) {
            score -= 15;  // 没有WHERE，全表扫描
        }

        // 使用LIKE '%xxx%'
        if (lowerSql.contains("like '%")) {
            score -= 5;  // 前缀通配符，索引失效
        }

        return Math.max(0, score);
    }

    /**
     * 评估安全性
     */
    private int evaluateSecurity(String sql) {
        int score = 20;

        // 已经通过isSqlSafe检查，这里给基础分
        score = 20;

        // 如果使用了参数化查询思维（虽然没有实际参数）
        if (!sql.contains("'") || sql.matches(".*=\\s*\\?.*")) {
            score -= 0;  // 看起来安全
        } else if (sql.contains("'") && sql.split("'").length > 3) {
            score -= 5;  // 多个字符串常量，可能有硬编码
        }

        return Math.max(0, score);
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        sqlCache.clear();
        log.info("SQL缓存已清除");
    }

    /**
     * 获取缓存统计
     */
    public Map<String, Object> getCacheStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("cacheSize", sqlCache.size());
        stats.put("totalRequests", totalRequests);
        stats.put("totalSuccess", totalSuccess);
        stats.put("totalFailures", totalFailures);
        stats.put("totalCacheHits", totalCacheHits);
        stats.put("cacheHitRate", totalRequests > 0 ?
                String.format("%.2f%%", (double) totalCacheHits / totalRequests * 100) : "0%");
        stats.put("avgGenerationTimeMs", totalSuccess > 0 ?
                totalTimeMs / totalSuccess : 0);

        return stats;
    }

    /**
     * 构建系统指令
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
                "5. 添加适当的LIMIT限制返回行数\n" +
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
        prompt.append("4. 考虑性能优化，选择合适的写法\n");
        prompt.append("5. 添加必要的LIMIT限制\n\n");

        prompt.append("【参考示例】\n");
        prompt.append("- 需求: 查询所有用户 -> SELECT id, username FROM user LIMIT 100\n");
        prompt.append("- 需求: 查询年龄大于18的用户 -> SELECT id, username FROM user WHERE age > 18 LIMIT 100\n");
        prompt.append("- 需求: 统计每个年龄的用户数量 -> SELECT age, COUNT(*) as count FROM user GROUP BY age\n\n");

        prompt.append("【生成的SQL】");

        return prompt.toString();
    }

    /**
     * SQL生成结果
     */
    @Data
    public static class SqlGenerationResult {
        private boolean success;
        private String question;
        private String sql;
        private String error;
        private boolean fromCache;
        private Long generationTimeMs;
        private QualityScore qualityScore;
    }

    /**
     * 质量评分
     */
    @Data
    public static class QualityScore {
        private int formatScore;        // 格式规范（0-20）
        private int practiceScore;      // 最佳实践（0-30）
        private int performanceScore;   // 性能考虑（0-30）
        private int securityScore;      // 安全性（0-20）
        private int overallScore;       // 总分（0-100）
        private String level;           // 评级：优秀/良好/一般/需改进
    }

    /**
     * 缓存的SQL
     */
    @Data
    private static class CachedSql {
        private String sql;
        private long timestamp;

        public CachedSql(String sql) {
            this.sql = sql;
            this.timestamp = System.currentTimeMillis();
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_EXPIRE_TIME;
        }
    }
}
