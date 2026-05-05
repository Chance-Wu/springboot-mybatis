package com.chance.component.ai;

import com.chance.controller.ai.SqlSecurityController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 结构化SQL结果构建器
 * 将SQL生成结果转换为结构化的响应格式
 *
 * @author chance
 * @date 2026/5/4 16:25
 * @since 1.0
 */
@Slf4j
@Component
public class StructuredSqlResultBuilder {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 构建完整的结构化结果
     */
    public StructuredSqlResult buildCompleteResult(
            String sql,
            String question,
            boolean fromCache,
            Long generationTimeMs,
            SqlSecurityController.SecurityCheckResult securityCheck,
            String schemaInfo) {

        StructuredSqlResult result = fromCache ?
                StructuredSqlResult.fromCache(sql, question) :
                StructuredSqlResult.success(sql, question);

        // 基本信息
        result.setFromCache(fromCache);
        result.setGenerationTimeMs(generationTimeMs);

        // 安全检查信息
        result.setSecurityInfo(buildSecurityInfo(securityCheck));

        // Schema信息
        result.setSchemaInfo(parseSchemaInfo(schemaInfo));

        // 质量评分
        result.setQualityScore(evaluateQuality(sql));

        // SQL分析
        analyzeSql(result, sql);

        // 性能建议
        result.setPerformanceSuggestions(generatePerformanceSuggestions(sql));

        // 元数据
        result.setMetadata(buildMetadata());

        return result;
    }

    /**
     * 构建安全信息
     */
    private StructuredSqlResult.SecurityInfo buildSecurityInfo(
            SqlSecurityController.SecurityCheckResult securityCheck) {

        StructuredSqlResult.SecurityInfo info = new StructuredSqlResult.SecurityInfo();
        info.setSafe(securityCheck.isSafe());
        info.setMessage(securityCheck.getMessage());
        info.setLevel(securityCheck.getLevel().name());

        // 执行的检查项
        List<String> checks = Arrays.asList(
                "只读操作检查",
                "危险关键字检测",
                "SQL注入防护",
                "注释检查",
                "LIMIT限制检查"
        );
        info.setChecks(checks);

        return info;
    }

    /**
     * 解析Schema信息
     */
    private StructuredSqlResult.SchemaInfo parseSchemaInfo(String schemaInfo) {
        StructuredSqlResult.SchemaInfo info = new StructuredSqlResult.SchemaInfo();

        if (schemaInfo == null || schemaInfo.isEmpty()) {
            info.setTotalTables(0);
            info.setTables(new ArrayList<>());
            return info;
        }

        // 简单的Schema解析（实际项目中可以更复杂）
        List<StructuredSqlResult.TableInfo> tables = new ArrayList<>();

        // 这里简化处理，实际应该解析完整的Schema字符串
        Pattern tablePattern = Pattern.compile("表名:\\s*(\\w+)");
        Matcher matcher = tablePattern.matcher(schemaInfo);

        while (matcher.find()) {
            StructuredSqlResult.TableInfo table = new StructuredSqlResult.TableInfo();
            table.setTableName(matcher.group(1));
            table.setUsedInQuery(true);
            tables.add(table);
        }

        info.setTables(tables);
        info.setTotalTables(tables.size());
        info.setDatabaseName("mysql");  // 默认数据库名

        return info;
    }

    /**
     * 评估SQL质量
     */
    private StructuredSqlResult.QualityScore evaluateQuality(String sql) {
        StructuredSqlResult.QualityScore score = new StructuredSqlResult.QualityScore();

        // 格式规范（20分）
        int formatScore = evaluateFormat(sql);
        score.setFormatScore(formatScore);

        // 最佳实践（30分）
        int practiceScore = evaluateBestPractices(sql);
        score.setPracticeScore(practiceScore);

        // 性能考虑（30分）
        int performanceScore = evaluatePerformance(sql);
        score.setPerformanceScore(performanceScore);

        // 安全性（20分）
        score.setSecurityScore(20);  // 已通过安全检查

        // 总分
        int overallScore = formatScore + practiceScore + performanceScore + 20;
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

        // 改进建议
        score.setSuggestions(generateImprovementSuggestions(sql, score));

        return score;
    }

    /**
     * 评估格式
     */
    private int evaluateFormat(String sql) {
        int score = 20;
        String lowerSql = sql.toLowerCase();

        // 关键字大写检查
        if (!sql.contains("SELECT") && !sql.contains("FROM")) {
            score -= 5;
        }

        // 避免SELECT *
        if (lowerSql.contains("select *")) {
            score -= 10;
        }

        // 格式化检查
        if (sql.contains("\n") && sql.trim().length() > 50) {
            // 有格式化，不扣分
        } else if (sql.trim().length() > 100) {
            score -= 5;
        }

        return Math.max(0, score);
    }

    /**
     * 评估最佳实践
     */
    private int evaluateBestPractices(String sql) {
        int score = 30;
        String lowerSql = sql.toLowerCase();

        // JOIN使用别名
        if (lowerSql.contains(" join ") && !lowerSql.contains(" as ")) {
            score -= 5;
        }

        // 避免在索引列上使用函数
        if (lowerSql.contains("year(") || lowerSql.contains("month(") ||
                lowerSql.contains("upper(")) {
            score -= 10;
        }

        return Math.max(0, score);
    }

    /**
     * 评估性能
     */
    private int evaluatePerformance(String sql) {
        int score = 30;
        String lowerSql = sql.toLowerCase();

        // 有LIMIT
        if (!lowerSql.contains("limit")) {
            score -= 10;
        }

        // 有WHERE条件
        if (!lowerSql.contains("where")) {
            score -= 15;
        }

        // 避免LIKE '%xxx%'
        if (lowerSql.contains("like '%")) {
            score -= 5;
        }

        return Math.max(0, score);
    }

    /**
     * 生成改进建议
     */
    private List<String> generateImprovementSuggestions(
            String sql,
            StructuredSqlResult.QualityScore score) {

        List<String> suggestions = new ArrayList<>();
        String lowerSql = sql.toLowerCase();

        if (lowerSql.contains("select *")) {
            suggestions.add("建议明确指定需要的字段，避免使用SELECT *");
        }

        if (!lowerSql.contains("limit")) {
            suggestions.add("建议添加LIMIT子句限制返回行数");
        }

        if (!lowerSql.contains("where") && !lowerSql.contains("join")) {
            suggestions.add("查询没有过滤条件，可能返回大量数据");
        }

        if (lowerSql.contains("like '%")) {
            suggestions.add("前缀通配符会导致索引失效，考虑使用全文索引");
        }

        if (score.getOverallScore() < 80) {
            suggestions.add("SQL质量有待提升，请参考最佳实践优化");
        }

        return suggestions;
    }

    /**
     * 分析SQL
     */
    private void analyzeSql(StructuredSqlResult result, String sql) {
        String lowerSql = sql.toLowerCase().trim();

        // 提取使用的表
        List<String> usedTables = extractTables(sql);
        result.setUsedTables(usedTables);

        // 提取使用的字段
        List<String> usedFields = extractFields(sql);
        result.setUsedFields(usedFields);

        // 判断SQL类型
        String sqlType = determineSqlType(sql);
        result.setSqlType(sqlType);

        // 判断复杂度
        String complexity = determineComplexity(sql);
        result.setComplexityLevel(complexity);
    }

    /**
     * 提取使用的表
     */
    private List<String> extractTables(String sql) {
        List<String> tables = new ArrayList<>();

        // 简单提取FROM和JOIN后的表名
        Pattern fromPattern = Pattern.compile("\\bfrom\\s+(\\w+)", Pattern.CASE_INSENSITIVE);
        Pattern joinPattern = Pattern.compile("\\bjoin\\s+(\\w+)", Pattern.CASE_INSENSITIVE);

        Matcher fromMatcher = fromPattern.matcher(sql);
        while (fromMatcher.find()) {
            tables.add(fromMatcher.group(1));
        }

        Matcher joinMatcher = joinPattern.matcher(sql);
        while (joinMatcher.find()) {
            tables.add(joinMatcher.group(1));
        }

        return tables;
    }

    /**
     * 提取使用的字段
     */
    private List<String> extractFields(String sql) {
        List<String> fields = new ArrayList<>();

        // 提取SELECT后的字段列表（简化版）
        Pattern selectPattern = Pattern.compile(
                "select\\s+(.*?)\\s+from",
                Pattern.CASE_INSENSITIVE | Pattern.DOTALL
        );

        Matcher matcher = selectPattern.matcher(sql);
        if (matcher.find()) {
            String fieldList = matcher.group(1);
            // 简单分割（实际应该更复杂的解析）
            String[] fieldArray = fieldList.split(",");
            for (String field : fieldArray) {
                String trimmed = field.trim();
                if (!trimmed.isEmpty() && !trimmed.equals("*")) {
                    // 去除别名部分
                    String fieldName = trimmed.split("\\s+as\\s+")[0]
                            .split("\\s+")[0];
                    fields.add(fieldName);
                }
            }
        }

        return fields;
    }

    /**
     * 判断SQL类型
     */
    private String determineSqlType(String sql) {
        String lowerSql = sql.toLowerCase();

        if (lowerSql.contains("group by")) {
            return "聚合查询";
        } else if (lowerSql.contains("join")) {
            return "JOIN查询";
        } else if (lowerSql.contains("union")) {
            return "UNION查询";
        } else if (lowerSql.contains("subquery") ||
                lowerSql.matches(".*\\(\\s*select\\s+.*")) {
            return "子查询";
        } else {
            return "简单查询";
        }
    }

    /**
     * 判断复杂度
     */
    private String determineComplexity(String sql) {
        String lowerSql = sql.toLowerCase();
        int complexity = 0;

        // 基于特征计算复杂度
        if (lowerSql.contains("join")) complexity += 2;
        if (lowerSql.contains("group by")) complexity += 1;
        if (lowerSql.contains("order by")) complexity += 1;
        if (lowerSql.contains("having")) complexity += 2;
        if (lowerSql.contains("union")) complexity += 2;
        if (lowerSql.matches(".*\\(\\s*select\\s+.*")) complexity += 3;

        if (complexity >= 6) {
            return "复杂";
        } else if (complexity >= 3) {
            return "中等";
        } else {
            return "简单";
        }
    }

    /**
     * 生成性能建议
     */
    private List<StructuredSqlResult.PerformanceSuggestion> generatePerformanceSuggestions(String sql) {
        List<StructuredSqlResult.PerformanceSuggestion> suggestions = new ArrayList<>();
        String lowerSql = sql.toLowerCase();

        // 检查是否有WHERE条件
        if (!lowerSql.contains("where")) {
            StructuredSqlResult.PerformanceSuggestion suggestion =
                    new StructuredSqlResult.PerformanceSuggestion();
            suggestion.setType("WARNING");
            suggestion.setMessage("查询没有WHERE条件，可能导致全表扫描");
            suggestion.setPriority("HIGH");
            suggestion.setAffectedClause("WHERE");
            suggestions.add(suggestion);
        }

        // 检查是否有LIMIT
        if (!lowerSql.contains("limit")) {
            StructuredSqlResult.PerformanceSuggestion suggestion =
                    new StructuredSqlResult.PerformanceSuggestion();
            suggestion.setType("WARNING");
            suggestion.setMessage("查询没有LIMIT限制，可能返回大量数据");
            suggestion.setPriority("MEDIUM");
            suggestion.setAffectedClause("LIMIT");
            suggestions.add(suggestion);
        }

        // 检查LIKE前缀通配符
        if (lowerSql.contains("like '%")) {
            StructuredSqlResult.PerformanceSuggestion suggestion =
                    new StructuredSqlResult.PerformanceSuggestion();
            suggestion.setType("OPTIMIZE");
            suggestion.setMessage("前缀通配符导致索引失效，建议使用全文索引或重构查询");
            suggestion.setPriority("HIGH");
            suggestion.setAffectedClause("WHERE");
            suggestions.add(suggestion);
        }

        // 检查SELECT *
        if (lowerSql.contains("select *")) {
            StructuredSqlResult.PerformanceSuggestion suggestion =
                    new StructuredSqlResult.PerformanceSuggestion();
            suggestion.setType("OPTIMIZE");
            suggestion.setMessage("使用SELECT *会返回所有字段，建议只查询需要的字段");
            suggestion.setPriority("MEDIUM");
            suggestion.setAffectedClause("SELECT");
            suggestions.add(suggestion);
        }

        // 检查JOIN数量
        int joinCount = lowerSql.split("join").length - 1;
        if (joinCount > 3) {
            StructuredSqlResult.PerformanceSuggestion suggestion =
                    new StructuredSqlResult.PerformanceSuggestion();
            suggestion.setType("WARNING");
            suggestion.setMessage("JOIN表数量过多（" + joinCount + "个），可能影响性能");
            suggestion.setPriority("HIGH");
            suggestion.setAffectedClause("JOIN");
            suggestions.add(suggestion);
        }

        return suggestions;
    }

    /**
     * 构建元数据
     */
    private StructuredSqlResult.Metadata buildMetadata() {
        StructuredSqlResult.Metadata metadata = new StructuredSqlResult.Metadata();
        metadata.setModel("Qwen-Max");
        metadata.setTimestamp(LocalDateTime.now().format(FORMATTER));
        metadata.setVersion("2.0.0");
        metadata.setExtra(new HashMap<>());

        return metadata;
    }
}
