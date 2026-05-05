package com.chance.controller.ai;

import com.chance.component.ai.StructuredSqlResult;
import com.chance.service.ai.OptimizedSmartSqlService;
import com.chance.service.ai.RagSchemaFusionService;
import com.chance.service.ai.SmartSqlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 智能SQL生成控制器
 * 支持自然语言到SQL的转换
 *
 * @author chance
 * @date 2026/5/4 16:15
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/smart-sql")
@Tag(name = "智能SQL生成", description = "基于RAG的自然语言转SQL接口")
public class SmartSqlController {

    @Resource
    private SmartSqlService smartSqlService;

    @Resource
    private RagSchemaFusionService ragSchemaFusionService;

    @Resource
    private OptimizedSmartSqlService optimizedSmartSqlService;

    @Resource
    private SqlSecurityController sqlSecurityController;

    /**
     * 根据自然语言生成SQL
     *
     * @param question 用户的自然语言查询需求
     * @return 生成的SQL语句
     */
    @PostMapping("/generate")
    @Operation(summary = "生成SQL", description = "根据自然语言描述生成SQL查询语句")
    public Map<String, Object> generateSql(@RequestParam String question) {
        log.info("收到SQL生成请求: {}", question);

        Map<String, Object> result = new HashMap<>();

        try {
            String sql = smartSqlService.generateSql(question);

            result.put("success", true);
            result.put("question", question);
            result.put("sql", sql);
            result.put("message", "SQL生成成功");

        } catch (Exception e) {
            log.error("SQL生成失败", e);
            result.put("success", false);
            result.put("error", "SQL生成失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * 针对特定表生成SQL
     *
     * @param tableName 表名
     * @param question  查询需求
     * @return 生成的SQL
     */
    @PostMapping("/generate-for-table")
    @Operation(summary = "为指定表生成SQL", description = "针对特定表生成SQL查询")
    public Map<String, Object> generateSqlForTable(
            @RequestParam String tableName,
            @RequestParam String question) {

        log.info("为表 {} 生成SQL: {}", tableName, question);

        Map<String, Object> result = new HashMap<>();

        try {
            String sql = smartSqlService.generateSqlForTable(tableName, question);

            result.put("success", true);
            result.put("tableName", tableName);
            result.put("question", question);
            result.put("sql", sql);

        } catch (Exception e) {
            log.error("SQL生成失败", e);
            result.put("success", false);
            result.put("error", "SQL生成失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * 解释SQL语句
     *
     * @param sql SQL语句
     * @return 解释说明
     */
    @PostMapping("/explain")
    @Operation(summary = "解释SQL", description = "用自然语言解释SQL语句的作用")
    public Map<String, Object> explainSql(@RequestParam String sql) {
        log.info("解释SQL: {}", sql);

        Map<String, Object> result = new HashMap<>();

        try {
            String explanation = smartSqlService.explainSql(sql);

            result.put("success", true);
            result.put("sql", sql);
            result.put("explanation", explanation);

        } catch (Exception e) {
            log.error("SQL解释失败", e);
            result.put("success", false);
            result.put("error", "SQL解释失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * 优化SQL语句
     *
     * @param sql 原始SQL
     * @return 优化后的SQL
     */
    @PostMapping("/optimize")
    @Operation(summary = "优化SQL", description = "优化SQL语句以提高性能")
    public Map<String, Object> optimizeSql(@RequestParam String sql) {
        log.info("优化SQL: {}", sql);

        Map<String, Object> result = new HashMap<>();

        try {
            String optimizedSql = smartSqlService.optimizeSql(sql);

            result.put("success", true);
            result.put("originalSql", sql);
            result.put("optimizedSql", optimizedSql);

        } catch (Exception e) {
            log.error("SQL优化失败", e);
            result.put("success", false);
            result.put("error", "SQL优化失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * 执行智能查询（生成SQL并返回）
     *
     * @param question 自然语言查询
     * @return 查询结果
     */
    @PostMapping("/query")
    @Operation(summary = "智能查询", description = "自然语言查询，自动生成SQL")
    public Map<String, Object> smartQuery(@RequestParam String question) {
        log.info("智能查询: {}", question);

        return smartSqlService.executeGeneratedSql(question);
    }

    /**
     * 获取数据库Schema信息
     *
     * @return Schema信息
     */
    @GetMapping("/schema")
    @Operation(summary = "获取Schema", description = "获取数据库表结构信息")
    public Map<String, Object> getSchema() {
        Map<String, Object> result = new HashMap<>();

        try {
            String schema = smartSqlService.getSchemaInfo();
            result.put("success", true);
            result.put("schema", schema);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    /**
     * 清除Schema缓存
     *
     * @return 操作结果
     */
    @PostMapping("/cache/clear")
    @Operation(summary = "清除缓存", description = "清除数据库Schema缓存")
    public Map<String, Object> clearCache() {
        Map<String, Object> result = new HashMap<>();

        try {
            smartSqlService.clearSchemaCache();
            result.put("success", true);
            result.put("message", "Schema缓存已清除");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    /**
     * 获取缓存统计信息
     *
     * @return 缓存统计
     */
    @GetMapping("/cache/stats")
    @Operation(summary = "缓存统计", description = "获取Schema缓存统计信息")
    public Map<String, Object> getCacheStats() {
        Map<String, Object> result = new HashMap<>();

        try {
            Map<String, Object> stats = smartSqlService.getSchemaCacheStats();
            result.put("success", true);
            result.putAll(stats);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    /**
     * RAG + Schema融合生成SQL
     *
     * @param question 自然语言查询
     * @return 生成的SQL
     */
    @PostMapping("/generate-fusion")
    @Operation(summary = "融合生成SQL", description = "结合业务知识和数据库Schema生成SQL")
    public Map<String, Object> generateSqlFusion(@RequestParam String question) {
        log.info("收到融合SQL生成请求: {}", question);

        Map<String, Object> result = new HashMap<>();

        try {
            String sql = ragSchemaFusionService.generateSqlWithFusion(question);

            result.put("success", true);
            result.put("question", question);
            result.put("sql", sql);
            result.put("type", "fusion");
            result.put("message", "使用RAG + Schema融合生成");

        } catch (Exception e) {
            log.error("融合SQL生成失败", e);
            result.put("success", false);
            result.put("error", "SQL生成失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * 添加自定义业务知识
     *
     * @param knowledge 业务知识文本
     * @return 操作结果
     */
    @PostMapping("/knowledge/add")
    @Operation(summary = "添加业务知识", description = "添加自定义的业务规则和最佳实践")
    public Map<String, Object> addKnowledge(@RequestParam String knowledge) {
        log.info("添加自定义业务知识");

        Map<String, Object> result = new HashMap<>();

        try {
            ragSchemaFusionService.addCustomKnowledge(knowledge);

            result.put("success", true);
            result.put("message", "业务知识添加成功");

        } catch (Exception e) {
            log.error("添加知识失败", e);
            result.put("success", false);
            result.put("error", "添加失败: " + e.getMessage());
        }

        return result;
    }

    /**
     * 获取知识库统计
     *
     * @return 统计信息
     */
    @GetMapping("/knowledge/stats")
    @Operation(summary = "知识库统计", description = "获取业务知识库统计信息")
    public Map<String, Object> getKnowledgeStats() {
        Map<String, Object> result = new HashMap<>();

        try {
            Map<String, Object> stats = ragSchemaFusionService.getKnowledgeStats();
            result.put("success", true);
            result.putAll(stats);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    /**
     * 优化的SQL生成（带缓存、重试、质量评估）
     *
     * @param question 自然语言查询
     * @return SQL生成结果（包含质量评分）
     */
    @PostMapping("/generate-optimized")
    @Operation(summary = "优化生成SQL", description = "带缓存、重试、质量评估的SQL生成")
    public OptimizedSmartSqlService.SqlGenerationResult generateSqlOptimized(@RequestParam String question) {
        log.info("收到优化SQL生成请求: {}", question);
        return optimizedSmartSqlService.generateSql(question);
    }

    /**
     * 结构化SQL生成（完整元数据）
     *
     * @param question 自然语言查询
     * @return 结构化的SQL生成结果
     */
    @PostMapping("/generate-structured")
    @Operation(summary = "结构化生成SQL", description = "返回包含完整元数据的结构化SQL结果")
    public StructuredSqlResult generateSqlStructured(@RequestParam String question) {
        log.info("收到结构化SQL生成请求: {}", question);
        return optimizedSmartSqlService.generateStructuredSql(question);
    }

    /**
     * 获取优化服务的缓存统计
     *
     * @return 缓存和性能统计
     */
    @GetMapping("/optimized/stats")
    @Operation(summary = "优化服务统计", description = "获取优化SQL生成服务的统计信息")
    public Map<String, Object> getOptimizedStats() {
        Map<String, Object> result = new HashMap<>();

        try {
            Map<String, Object> stats = optimizedSmartSqlService.getCacheStats();
            result.put("success", true);
            result.putAll(stats);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    /**
     * 清除优化服务的缓存
     *
     * @return 操作结果
     */
    @PostMapping("/optimized/cache/clear")
    @Operation(summary = "清除优化缓存", description = "清除优化SQL生成服务的缓存")
    public Map<String, Object> clearOptimizedCache() {
        Map<String, Object> result = new HashMap<>();

        try {
            optimizedSmartSqlService.clearCache();
            result.put("success", true);
            result.put("message", "优化服务缓存已清除");
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    /**
     * SQL安全检查
     *
     * @param sql 待检查的SQL语句
     * @return 安全检查结果
     */
    @PostMapping("/security/check")
    @Operation(summary = "SQL安全检查", description = "检查SQL语句的安全性")
    public SqlSecurityController.SecurityCheckResult checkSqlSecurity(@RequestParam String sql) {
        log.info("收到SQL安全检查请求");
        return sqlSecurityController.checkSqlSecurity(sql);
    }

    /**
     * SQL验证并修复
     *
     * @param sql        原始SQL
     * @param strictMode 是否启用严格模式
     * @return 验证和修复结果
     */
    @PostMapping("/security/validate")
    @Operation(summary = "SQL验证并修复", description = "验证SQL安全性并自动修复")
    public SqlSecurityController.ValidationResult validateAndFixSql(
            @RequestParam String sql,
            @RequestParam(defaultValue = "false") boolean strictMode) {
        log.info("收到SQL验证请求，严格模式: {}", strictMode);
        return sqlSecurityController.validateAndFix(sql, strictMode);
    }

    /**
     * 自动添加LIMIT限制
     *
     * @param sql     原始SQL
     * @param maxRows 最大返回行数（可选）
     * @return 添加LIMIT后的SQL
     */
    @PostMapping("/security/add-limit")
    @Operation(summary = "添加LIMIT限制", description = "自动为SQL添加LIMIT子句")
    public Map<String, Object> addLimitClause(
            @RequestParam String sql,
            @RequestParam(required = false) Integer maxRows) {
        log.info("收到添加LIMIT请求");

        Map<String, Object> result = new HashMap<>();

        try {
            String limitedSql;
            if (maxRows != null) {
                limitedSql = sqlSecurityController.addLimitClause(sql, maxRows);
            } else {
                limitedSql = sqlSecurityController.addDefaultLimit(sql);
            }

            result.put("success", true);
            result.put("originalSql", sql);
            result.put("limitedSql", limitedSql);
            result.put("maxRows", maxRows != null ? maxRows : 100);

        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }

        return result;
    }
}
