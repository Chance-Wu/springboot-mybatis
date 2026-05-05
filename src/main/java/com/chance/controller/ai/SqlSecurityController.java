package com.chance.controller.ai;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL安全控制器
 * 提供SQL安全检查、注入防护、权限控制等功能
 *
 * @author chance
 * @date 2026/5/4 16:20
 * @since 1.0
 */
@Slf4j
@Component
public class SqlSecurityController {

    // 默认最大返回行数
    private static final int DEFAULT_MAX_ROWS = 100;

    // 严格模式最大返回行数
    private static final int STRICT_MAX_ROWS = 50;

    // 危险关键字列表（写操作）
    private static final List<String> DANGEROUS_WRITE_KEYWORDS = Arrays.asList(
            "INSERT", "UPDATE", "DELETE", "DROP", "ALTER", "CREATE",
            "TRUNCATE", "REPLACE", "MERGE"
    );

    // 危险关键字列表（系统操作）
    private static final List<String> DANGEROUS_SYSTEM_KEYWORDS = Arrays.asList(
            "GRANT", "REVOKE", "SET PASSWORD", "KILL", "SHUTDOWN",
            "LOAD_FILE", "INTO OUTFILE", "INTO DUMPFILE",
            "EXEC", "EXECUTE", "XP_", "SP_"
    );

    // SQL注入特征
    private static final List<Pattern> SQL_INJECTION_PATTERNS = Arrays.asList(
            Pattern.compile("('\\s*(OR|AND)\\s*')", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(--\\s*$)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(/\\*.*?\\*/)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(;\\s*(DROP|DELETE|UPDATE|INSERT))", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(UNION\\s+(ALL\\s+)?SELECT)", Pattern.CASE_INSENSITIVE)
    );

    /**
     * SQL安全检查结果
     */
    public static class SecurityCheckResult {
        private boolean safe;
        private String message;
        private SecurityLevel level;

        public enum SecurityLevel {
            SAFE,           // 安全
            WARNING,        // 警告
            DANGEROUS       // 危险
        }

        public static SecurityCheckResult safe() {
            SecurityCheckResult result = new SecurityCheckResult();
            result.safe = true;
            result.message = "SQL安全检查通过";
            result.level = SecurityLevel.SAFE;
            return result;
        }

        public static SecurityCheckResult warning(String message) {
            SecurityCheckResult result = new SecurityCheckResult();
            result.safe = true;
            result.message = message;
            result.level = SecurityLevel.WARNING;
            return result;
        }

        public static SecurityCheckResult dangerous(String message) {
            SecurityCheckResult result = new SecurityCheckResult();
            result.safe = false;
            result.message = message;
            result.level = SecurityLevel.DANGEROUS;
            return result;
        }

        public boolean isSafe() {
            return safe;
        }

        public String getMessage() {
            return message;
        }

        public SecurityLevel getLevel() {
            return level;
        }
    }

    /**
     * 全面的SQL安全检查
     *
     * @param sql 待检查的SQL语句
     * @return 安全检查结果
     */
    public SecurityCheckResult checkSqlSecurity(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            return SecurityCheckResult.dangerous("SQL语句为空");
        }

        String normalizedSql = normalizeSql(sql);

        // 1. 检查是否为只读操作
        SecurityCheckResult readOnlyCheck = checkReadOnly(normalizedSql);
        if (!readOnlyCheck.isSafe()) {
            return readOnlyCheck;
        }

        // 2. 检查危险关键字
        SecurityCheckResult keywordCheck = checkDangerousKeywords(normalizedSql);
        if (!keywordCheck.isSafe()) {
            return keywordCheck;
        }

        // 3. 检查SQL注入特征
        SecurityCheckResult injectionCheck = checkSqlInjection(normalizedSql);
        if (!injectionCheck.isSafe()) {
            return injectionCheck;
        }

        // 4. 检查注释
        SecurityCheckResult commentCheck = checkComments(normalizedSql);
        if (!commentCheck.isSafe()) {
            return commentCheck;
        }

        // 5. 检查是否有LIMIT
        SecurityCheckResult limitCheck = checkLimitClause(normalizedSql);
        if (limitCheck.getLevel() == SecurityCheckResult.SecurityLevel.WARNING) {
            return limitCheck;
        }

        return SecurityCheckResult.safe();
    }

    /**
     * 检查是否为只读操作
     */
    private SecurityCheckResult checkReadOnly(String sql) {
        String upperSql = sql.toUpperCase().trim();

        // 只允许SELECT语句
        if (!upperSql.startsWith("SELECT") && !upperSql.startsWith("WITH")) {
            log.warn("检测到非只读操作: {}", sql);
            return SecurityCheckResult.dangerous(
                    "不允许执行非只读操作，只支持SELECT查询"
            );
        }

        return SecurityCheckResult.safe();
    }

    /**
     * 检查危险关键字
     */
    private SecurityCheckResult checkDangerousKeywords(String sql) {
        String upperSql = sql.toUpperCase();

        // 检查写操作关键字（使用单词边界匹配）
        for (String keyword : DANGEROUS_WRITE_KEYWORDS) {
            // 使用正则表达式匹配完整单词，避免子字符串误判
            // 例如：INTERVAL 不应该匹配 CREATE
            Pattern pattern = Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(sql).find()) {
                log.warn("检测到危险写操作关键字: {}", keyword);
                return SecurityCheckResult.dangerous(
                        "检测到危险操作关键字: " + keyword + "，不允许执行"
                );
            }
        }

        // 检查系统操作关键字（使用单词边界匹配）
        for (String keyword : DANGEROUS_SYSTEM_KEYWORDS) {
            Pattern pattern = Pattern.compile("\\b" + keyword.replace(" ", "\\s+") + "\\b", Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(sql).find()) {
                log.warn("检测到危险系统操作关键字: {}", keyword);
                return SecurityCheckResult.dangerous(
                        "检测到危险系统操作关键字: " + keyword + "，不允许执行"
                );
            }
        }

        return SecurityCheckResult.safe();
    }

    /**
     * 检查SQL注入特征
     */
    private SecurityCheckResult checkSqlInjection(String sql) {
        for (Pattern pattern : SQL_INJECTION_PATTERNS) {
            Matcher matcher = pattern.matcher(sql);
            if (matcher.find()) {
                log.warn("检测到SQL注入特征: {}", matcher.group());
                return SecurityCheckResult.dangerous(
                        "检测到潜在的SQL注入攻击，拒绝执行"
                );
            }
        }

        return SecurityCheckResult.safe();
    }

    /**
     * 检查注释
     */
    private SecurityCheckResult checkComments(String sql) {
        // 检查单行注释
        if (sql.contains("--")) {
            log.warn("SQL中包含单行注释，可能存在注入风险");
            return SecurityCheckResult.dangerous(
                    "SQL中不允许包含注释（--），可能存在安全风险"
            );
        }

        // 检查多行注释
        if (sql.contains("/*") || sql.contains("*/")) {
            log.warn("SQL中包含多行注释，可能存在注入风险");
            return SecurityCheckResult.dangerous(
                    "SQL中不允许包含注释（/* */），可能存在安全风险"
            );
        }

        return SecurityCheckResult.safe();
    }

    /**
     * 检查LIMIT子句
     */
    private SecurityCheckResult checkLimitClause(String sql) {
        String lowerSql = sql.toLowerCase();

        // 检查是否有LIMIT
        if (!lowerSql.contains("limit")) {
            log.warn("SQL中没有LIMIT子句，可能返回大量数据");
            return SecurityCheckResult.warning(
                    "SQL中没有LIMIT限制，建议添加LIMIT子句以控制返回行数"
            );
        }

        // 检查LIMIT值是否过大
        Pattern limitPattern = Pattern.compile("limit\\s+(\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = limitPattern.matcher(sql);
        if (matcher.find()) {
            int limitValue = Integer.parseInt(matcher.group(1));
            if (limitValue > DEFAULT_MAX_ROWS) {
                log.warn("LIMIT值过大: {}", limitValue);
                return SecurityCheckResult.warning(
                        "LIMIT值过大（" + limitValue + "），建议不超过" + DEFAULT_MAX_ROWS
                );
            }
        }

        return SecurityCheckResult.safe();
    }

    /**
     * 自动添加LIMIT限制
     *
     * @param sql     原始SQL
     * @param maxRows 最大返回行数
     * @return 添加LIMIT后的SQL
     */
    public String addLimitClause(String sql, int maxRows) {
        if (sql == null || sql.trim().isEmpty()) {
            return sql;
        }

        String normalizedSql = normalizeSql(sql);
        String lowerSql = normalizedSql.toLowerCase();

        // 如果已经有LIMIT，不重复添加
        if (lowerSql.contains("limit")) {
            log.debug("SQL已包含LIMIT子句，无需添加");
            return normalizedSql;
        }

        // 只在SELECT语句末尾添加LIMIT
        if (lowerSql.startsWith("select") || lowerSql.startsWith("with")) {
            String limitedSql = normalizedSql.trim() + " LIMIT " + maxRows;
            log.info("自动添加LIMIT {}: {}", maxRows, limitedSql);
            return limitedSql;
        }

        return normalizedSql;
    }

    /**
     * 自动添加LIMIT限制（使用默认值）
     *
     * @param sql 原始SQL
     * @return 添加LIMIT后的SQL
     */
    public String addDefaultLimit(String sql) {
        return addLimitClause(sql, DEFAULT_MAX_ROWS);
    }

    /**
     * 严格模式：添加更小的LIMIT限制
     *
     * @param sql 原始SQL
     * @return 添加LIMIT后的SQL
     */
    public String addStrictLimit(String sql) {
        return addLimitClause(sql, STRICT_MAX_ROWS);
    }

    /**
     * 验证并修复SQL
     * 如果SQL不安全，尝试修复或拒绝
     *
     * @param sql        原始SQL
     * @param strictMode 是否启用严格模式
     * @return 修复后的SQL或错误信息
     */
    public ValidationResult validateAndFix(String sql, boolean strictMode) {
        ValidationResult result = new ValidationResult();
        result.setOriginalSql(sql);

        // 1. 安全检查
        SecurityCheckResult securityCheck = checkSqlSecurity(sql);
        result.setSecurityCheck(securityCheck);

        if (!securityCheck.isSafe()) {
            result.setValid(false);
            result.setMessage("SQL安全检查失败: " + securityCheck.getMessage());
            return result;
        }

        // 2. 自动添加LIMIT
        String fixedSql = strictMode ? addStrictLimit(sql) : addDefaultLimit(sql);
        result.setFixedSql(fixedSql);
        result.setValid(true);
        result.setMessage("SQL验证通过，已自动添加LIMIT限制");

        return result;
    }

    /**
     * 标准化SQL（去除多余空格、统一格式）
     */
    private String normalizeSql(String sql) {
        if (sql == null) {
            return null;
        }

        // 去除首尾空格
        String normalized = sql.trim();

        // 清理Markdown代码块标记
        normalized = normalized
                .replaceAll("^```sql\\s*", "")  // 去除开头的 ```sql
                .replaceAll("^```\\s*", "")      // 去除开头的 ```
                .replaceAll("\\s*```$", "")      // 去除结尾的 ```
                .trim();

        // 将多个连续空格替换为单个空格
        normalized = normalized.replaceAll("\\s+", " ");

        return normalized;
    }

    /**
     * 验证结果
     */
    public static class ValidationResult {
        private boolean valid;
        private String originalSql;
        private String fixedSql;
        private String message;
        private SecurityCheckResult securityCheck;

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public String getOriginalSql() {
            return originalSql;
        }

        public void setOriginalSql(String originalSql) {
            this.originalSql = originalSql;
        }

        public String getFixedSql() {
            return fixedSql;
        }

        public void setFixedSql(String fixedSql) {
            this.fixedSql = fixedSql;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public SecurityCheckResult getSecurityCheck() {
            return securityCheck;
        }

        public void setSecurityCheck(SecurityCheckResult securityCheck) {
            this.securityCheck = securityCheck;
        }
    }
}
