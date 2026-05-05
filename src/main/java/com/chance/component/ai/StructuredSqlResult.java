package com.chance.component.ai;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 结构化SQL生成结果
 * 提供完整的SQL生成信息和元数据
 *
 * @author chance
 * @date 2026/5/4 16:25
 * @since 1.0
 */
@Data
public class StructuredSqlResult {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 生成的SQL语句
     */
    private String sql;

    /**
     * 原始问题
     */
    private String question;

    /**
     * 错误信息（如果失败）
     */
    private String error;

    /**
     * 是否来自缓存
     */
    private boolean fromCache;

    /**
     * 生成耗时（毫秒）
     */
    private Long generationTimeMs;

    /**
     * 质量评分
     */
    private QualityScore qualityScore;

    /**
     * 安全检查结果
     */
    private SecurityInfo securityInfo;

    /**
     * Schema信息
     */
    private SchemaInfo schemaInfo;

    /**
     * 性能建议
     */
    private List<PerformanceSuggestion> performanceSuggestions;

    /**
     * 使用的表列表
     */
    private List<String> usedTables;

    /**
     * 使用的字段列表
     */
    private List<String> usedFields;

    /**
     * SQL类型（简单查询、聚合查询、JOIN查询等）
     */
    private String sqlType;

    /**
     * 复杂度等级（简单、中等、复杂）
     */
    private String complexityLevel;

    /**
     * 元数据
     */
    private Metadata metadata;

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
        private List<String> suggestions; // 改进建议
    }

    /**
     * 安全信息
     */
    @Data
    public static class SecurityInfo {
        private boolean safe;
        private String message;
        private String level;  // SAFE/WARNING/DANGEROUS
        private List<String> checks;  // 执行的检查项
    }

    /**
     * Schema信息
     */
    @Data
    public static class SchemaInfo {
        private List<TableInfo> tables;
        private int totalTables;
        private String databaseName;
    }

    /**
     * 表信息
     */
    @Data
    public static class TableInfo {
        private String tableName;
        private String tableComment;
        private List<ColumnInfo> columns;
        private boolean usedInQuery;
    }

    /**
     * 字段信息
     */
    @Data
    public static class ColumnInfo {
        private String columnName;
        private String typeName;
        private boolean primaryKey;
        private boolean nullable;
        private String comment;
    }

    /**
     * 性能建议
     */
    @Data
    public static class PerformanceSuggestion {
        private String type;        // 建议类型：INDEX/OPTIMIZE/WARNING
        private String message;     // 建议内容
        private String priority;    // 优先级：HIGH/MEDIUM/LOW
        private String affectedClause; // 影响的SQL子句
    }

    /**
     * 元数据
     */
    @Data
    public static class Metadata {
        private String model;               // 使用的AI模型
        private Long tokenUsage;            // Token使用量
        private String timestamp;           // 生成时间
        private String version;             // 服务版本
        private Map<String, Object> extra;  // 额外信息
    }

    /**
     * 创建成功结果
     */
    public static StructuredSqlResult success(String sql, String question) {
        StructuredSqlResult result = new StructuredSqlResult();
        result.setSuccess(true);
        result.setSql(sql);
        result.setQuestion(question);
        result.setFromCache(false);
        return result;
    }

    /**
     * 创建失败结果
     */
    public static StructuredSqlResult failure(String error, String question) {
        StructuredSqlResult result = new StructuredSqlResult();
        result.setSuccess(false);
        result.setError(error);
        result.setQuestion(question);
        return result;
    }

    /**
     * 创建缓存命中结果
     */
    public static StructuredSqlResult fromCache(String sql, String question) {
        StructuredSqlResult result = new StructuredSqlResult();
        result.setSuccess(true);
        result.setSql(sql);
        result.setQuestion(question);
        result.setFromCache(true);
        result.setGenerationTimeMs(0L);
        return result;
    }
}
