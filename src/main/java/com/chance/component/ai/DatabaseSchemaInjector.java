package com.chance.component.ai;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库结构注入器
 * 将数据库表结构以AI友好的格式注入到Prompt中
 * 支持缓存、过滤和格式化优化
 *
 * @author chance
 * @date 2026/5/4 16:20
 * @since 1.0
 */
@Slf4j
@Component
public class DatabaseSchemaInjector {

    @Resource
    private DataSource dataSource;

    // Schema缓存（表名 -> Schema信息）
    private final Map<String, TableSchema> schemaCache = new ConcurrentHashMap<>();

    // 缓存过期时间（毫秒）- 默认1小时
    private static final long CACHE_EXPIRE_TIME = 3600 * 1000;

    // 最后更新时间
    private volatile long lastUpdateTime = 0;

    /**
     * 表结构信息
     */
    @Data
    public static class TableSchema {
        private String tableName;           // 表名
        private String tableComment;        // 表注释
        private List<ColumnInfo> columns;   // 字段列表
        private List<IndexInfo> indexes;    // 索引列表
        private List<String> sampleData;    // 示例数据
        private long timestamp;             // 时间戳

        public TableSchema() {
            this.timestamp = System.currentTimeMillis();
        }

        public boolean isExpired() {
            return System.currentTimeMillis() - timestamp > CACHE_EXPIRE_TIME;
        }
    }

    /**
     * 字段信息
     */
    @Data
    public static class ColumnInfo {
        private String columnName;      // 字段名
        private String typeName;        // 数据类型
        private Integer columnSize;     // 字段长度
        private boolean nullable;       // 是否可空
        private String defaultValue;    // 默认值
        private boolean primaryKey;     // 是否主键
        private boolean autoIncrement;  // 是否自增
        private String comment;         // 字段注释
    }

    /**
     * 索引信息
     */
    @Data
    public static class IndexInfo {
        private String indexName;       // 索引名
        private boolean unique;         // 是否唯一索引
        private List<String> columns;   // 索引字段
    }

    /**
     * 注入所有表的Schema（使用缓存）
     *
     * @return AI友好的Schema字符串
     */
    public String injectAllSchemas() {
        log.debug("注入所有表的Schema信息");

        // 检查缓存是否需要更新
        if (needRefreshCache()) {
            refreshCache();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== 数据库结构信息 ===\n\n");
        sb.append("数据库包含 ").append(schemaCache.size()).append(" 个表：\n\n");

        for (Map.Entry<String, TableSchema> entry : schemaCache.entrySet()) {
            TableSchema schema = entry.getValue();
            sb.append(formatTableSchema(schema));
            sb.append("\n---\n\n");
        }

        return sb.toString();
    }

    /**
     * 注入指定表的Schema
     *
     * @param tableName 表名
     * @return AI友好的Schema字符串
     */
    public String injectTableSchema(String tableName) {
        log.debug("注入表 {} 的Schema", tableName);

        // 检查缓存
        TableSchema schema = schemaCache.get(tableName.toLowerCase());
        if (schema == null || schema.isExpired()) {
            try (Connection conn = dataSource.getConnection()) {
                schema = extractTableSchemaFromConnection(conn, tableName);
            } catch (SQLException e) {
                log.error("提取表Schema失败: {}", tableName, e);
                throw new RuntimeException("提取表Schema失败: " + tableName, e);
            }
            schemaCache.put(tableName.toLowerCase(), schema);
        }

        return formatTableSchema(schema);
    }

    /**
     * 注入相关表的Schema（智能选择）
     * 根据问题关键词匹配相关的表
     *
     * @param question 用户问题
     * @return 相关表的Schema
     */
    public String injectRelevantSchemas(String question) {
        log.debug("根据问题注入相关表Schema: {}", question);

        // 确保缓存已加载
        if (needRefreshCache()) {
            refreshCache();
        }

        // 提取问题中的关键词
        Set<String> keywords = extractKeywords(question);

        // 匹配相关的表
        List<TableSchema> relevantTables = findRelevantTables(keywords);

        StringBuilder sb = new StringBuilder();
        sb.append("=== 相关数据库结构 ===\n\n");
        sb.append("找到 ").append(relevantTables.size()).append(" 个相关表：\n\n");

        for (TableSchema schema : relevantTables) {
            sb.append(formatTableSchema(schema));
            sb.append("\n---\n\n");
        }

        return sb.toString();
    }

    /**
     * 注入精简版Schema（只包含关键信息）
     *
     * @return 精简的Schema字符串
     */
    public String injectLiteSchema() {
        log.debug("注入精简版Schema");

        if (needRefreshCache()) {
            refreshCache();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== 数据库结构（精简版）===\n\n");

        for (TableSchema schema : schemaCache.values()) {
            sb.append("表: ").append(schema.getTableName());
            if (schema.getTableComment() != null && !schema.getTableComment().isEmpty()) {
                sb.append(" (").append(schema.getTableComment()).append(")");
            }
            sb.append("\n");

            // 只显示字段名和类型
            for (ColumnInfo col : schema.getColumns()) {
                sb.append(String.format("  - %s (%s)", col.getColumnName(), col.getTypeName()));
                if (col.isPrimaryKey()) {
                    sb.append(" [PK]");
                }
                if (col.getComment() != null && !col.getComment().isEmpty()) {
                    sb.append(" # ").append(col.getComment());
                }
                sb.append("\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * 获取表之间的关系信息
     *
     * @return 表关系描述
     */
    public String injectTableRelationships() {
        log.debug("注入表关系信息");

        if (needRefreshCache()) {
            refreshCache();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== 表关系信息 ===\n\n");

        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();

            for (String tableName : schemaCache.keySet()) {
                ResultSet rs = metaData.getImportedKeys(conn.getCatalog(), null, tableName);

                List<String> relationships = new ArrayList<>();
                while (rs.next()) {
                    String pkTable = rs.getString("PKTABLE_NAME");
                    String fkColumn = rs.getString("FKCOLUMN_NAME");
                    String pkColumn = rs.getString("PKCOLUMN_NAME");

                    relationships.add(String.format("  - %s.%s -> %s.%s (外键)",
                            tableName, fkColumn, pkTable, pkColumn));
                }
                rs.close();

                if (!relationships.isEmpty()) {
                    sb.append("表 ").append(tableName).append(" 的外键关系：\n");
                    for (String rel : relationships) {
                        sb.append(rel).append("\n");
                    }
                    sb.append("\n");
                }
            }
        } catch (SQLException e) {
            log.error("获取表关系失败", e);
        }

        return sb.toString();
    }

    /**
     * 刷新缓存
     */
    private synchronized void refreshCache() {
        log.info("刷新数据库Schema缓存");

        try {
            Map<String, TableSchema> newCache = new ConcurrentHashMap<>();

            try (Connection conn = dataSource.getConnection()) {
                List<String> tables = getAllTables(conn);

                for (String tableName : tables) {
                    try {
                        TableSchema schema = extractTableSchemaFromConnection(conn, tableName);
                        newCache.put(tableName.toLowerCase(), schema);
                        log.debug("缓存表Schema: {}", tableName);
                    } catch (Exception e) {
                        log.error("提取表Schema失败: {}", tableName, e);
                    }
                }
            }

            schemaCache.clear();
            schemaCache.putAll(newCache);
            lastUpdateTime = System.currentTimeMillis();

            log.info("Schema缓存刷新完成，共 {} 个表", schemaCache.size());

        } catch (Exception e) {
            log.error("刷新Schema缓存失败", e);
        }
    }

    /**
     * 判断是否需要刷新缓存
     */
    private boolean needRefreshCache() {
        return schemaCache.isEmpty() ||
                System.currentTimeMillis() - lastUpdateTime > CACHE_EXPIRE_TIME;
    }

    /**
     * 从数据库连接提取单个表的Schema
     */
    private TableSchema extractTableSchemaFromConnection(Connection conn, String tableName) throws SQLException {
        TableSchema schema = new TableSchema();
        schema.setTableName(tableName);

        // 提取字段信息
        schema.setColumns(extractColumns(conn, tableName));

        // 提取索引信息
        schema.setIndexes(extractIndexes(conn, tableName));

        // 提取表注释
        schema.setTableComment(getTableComment(conn, tableName));

        // 提取示例数据（前3条）
        schema.setSampleData(getSampleData(conn, tableName, 3));

        return schema;
    }

    /**
     * 提取字段信息
     */
    private List<ColumnInfo> extractColumns(Connection conn, String tableName) throws SQLException {
        List<ColumnInfo> columns = new ArrayList<>();
        DatabaseMetaData metaData = conn.getMetaData();

        // 获取列信息
        ResultSet rs = metaData.getColumns(conn.getCatalog(), null, tableName, "%");
        Map<String, ColumnInfo> columnMap = new LinkedHashMap<>();

        while (rs.next()) {
            ColumnInfo col = new ColumnInfo();
            String columnName = rs.getString("COLUMN_NAME");

            col.setColumnName(columnName);
            col.setTypeName(rs.getString("TYPE_NAME"));
            col.setColumnSize(rs.getInt("COLUMN_SIZE"));
            col.setNullable("YES".equals(rs.getString("IS_NULLABLE")));
            col.setDefaultValue(rs.getString("COLUMN_DEF"));
            col.setComment(rs.getString("REMARKS"));

            columnMap.put(columnName, col);
        }
        rs.close();

        // 获取主键信息
        rs = metaData.getPrimaryKeys(conn.getCatalog(), null, tableName);
        Set<String> primaryKeys = new HashSet<>();
        while (rs.next()) {
            primaryKeys.add(rs.getString("COLUMN_NAME"));
        }
        rs.close();

        // 标记主键
        for (ColumnInfo col : columnMap.values()) {
            if (primaryKeys.contains(col.getColumnName())) {
                col.setPrimaryKey(true);
            }
        }

        // 获取AUTO_INCREMENT信息
        try (Statement stmt = conn.createStatement();
             ResultSet infoRs = stmt.executeQuery("SHOW COLUMNS FROM " + tableName)) {

            while (infoRs.next()) {
                String columnName = infoRs.getString("Field");
                String extra = infoRs.getString("Extra");

                ColumnInfo col = columnMap.get(columnName);
                if (col != null && "auto_increment".equalsIgnoreCase(extra)) {
                    col.setAutoIncrement(true);
                }
            }
        }

        columns.addAll(columnMap.values());
        return columns;
    }

    /**
     * 提取索引信息
     */
    private List<IndexInfo> extractIndexes(Connection conn, String tableName) throws SQLException {
        List<IndexInfo> indexes = new ArrayList<>();
        DatabaseMetaData metaData = conn.getMetaData();

        ResultSet rs = metaData.getIndexInfo(conn.getCatalog(), null, tableName, false, false);

        Map<String, IndexInfo> indexMap = new LinkedHashMap<>();
        while (rs.next()) {
            String indexName = rs.getString("INDEX_NAME");
            String columnName = rs.getString("COLUMN_NAME");
            boolean nonUnique = rs.getBoolean("NON_UNIQUE");

            if (indexName == null) continue; // 跳过非索引

            IndexInfo index = indexMap.computeIfAbsent(indexName, k -> {
                IndexInfo idx = new IndexInfo();
                idx.setIndexName(indexName);
                idx.setUnique(!nonUnique);
                idx.setColumns(new ArrayList<>());
                return idx;
            });

            index.getColumns().add(columnName);
        }
        rs.close();

        indexes.addAll(indexMap.values());
        return indexes;
    }

    /**
     * 获取示例数据
     */
    private List<String> getSampleData(Connection conn, String tableName, int limit) {
        List<String> samples = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " LIMIT " + limit)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 列名
            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) header.append(" | ");
                header.append(metaData.getColumnName(i));
            }
            samples.add(header.toString());

            // 数据行
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1) row.append(" | ");
                    Object value = rs.getObject(i);
                    row.append(value != null ? value.toString() : "NULL");
                }
                samples.add(row.toString());
            }

        } catch (SQLException e) {
            log.warn("获取示例数据失败: {}", tableName, e);
        }

        return samples;
    }

    /**
     * 格式化表Schema为AI友好的字符串
     */
    private String formatTableSchema(TableSchema schema) {
        StringBuilder sb = new StringBuilder();

        sb.append("【表】").append(schema.getTableName());
        if (schema.getTableComment() != null && !schema.getTableComment().isEmpty()) {
            sb.append(" - ").append(schema.getTableComment());
        }
        sb.append("\n");

        // 字段信息
        sb.append("字段:\n");
        for (ColumnInfo col : schema.getColumns()) {
            sb.append(String.format("  - %s (%s)", col.getColumnName(), col.getTypeName()));

            if (col.isPrimaryKey()) {
                sb.append(", PRIMARY KEY");
            }
            if (col.isAutoIncrement()) {
                sb.append(", AUTO_INCREMENT");
            }
            if (!col.isNullable()) {
                sb.append(", NOT NULL");
            }
            if (col.getDefaultValue() != null) {
                sb.append(", DEFAULT: ").append(col.getDefaultValue());
            }
            if (col.getComment() != null && !col.getComment().isEmpty()) {
                sb.append(", 注释: ").append(col.getComment());
            }

            sb.append("\n");
        }

        // 索引信息
        if (!schema.getIndexes().isEmpty()) {
            sb.append("索引:\n");
            for (IndexInfo idx : schema.getIndexes()) {
                if ("PRIMARY".equals(idx.getIndexName())) continue; // 跳过主键

                sb.append(String.format("  - %s (%s): %s\n",
                        idx.getIndexName(),
                        idx.isUnique() ? "UNIQUE" : "INDEX",
                        String.join(", ", idx.getColumns())));
            }
        }

        // 示例数据
        if (!schema.getSampleData().isEmpty()) {
            sb.append("示例数据:\n");
            for (String sample : schema.getSampleData()) {
                sb.append("  ").append(sample).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * 获取所有表名
     */
    private List<String> getAllTables(Connection conn) throws SQLException {
        List<String> tables = new ArrayList<>();
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getTables(conn.getCatalog(), null, "%", new String[]{"TABLE"});

        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            // 排除系统表
            if (!tableName.toLowerCase().startsWith("sys_")
                    && !tableName.toLowerCase().startsWith("information_schema")) {
                tables.add(tableName);
            }
        }
        rs.close();

        return tables;
    }

    /**
     * 获取表注释
     */
    private String getTableComment(Connection conn, String tableName) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     String.format("SELECT TABLE_COMMENT FROM information_schema.TABLES " +
                                     "WHERE TABLE_SCHEMA = '%s' AND TABLE_NAME = '%s'",
                             conn.getCatalog(), tableName))) {

            if (rs.next()) {
                return rs.getString("TABLE_COMMENT");
            }
        }
        return null;
    }

    /**
     * 从问题中提取关键词
     */
    private Set<String> extractKeywords(String question) {
        Set<String> keywords = new HashSet<>();

        // 简单的中文分词（按空格和标点分割）
        String[] words = question.split("[\\s,，。！？、]+");

        for (String word : words) {
            if (word.length() > 1) { // 忽略单字
                keywords.add(word.toLowerCase());
            }
        }

        return keywords;
    }

    /**
     * 查找相关的表
     */
    private List<TableSchema> findRelevantTables(Set<String> keywords) {
        List<TableSchema> relevant = new ArrayList<>();

        for (TableSchema schema : schemaCache.values()) {
            boolean matched = false;

            // 匹配表名
            if (keywords.stream().anyMatch(k -> schema.getTableName().toLowerCase().contains(k))) {
                matched = true;
            }

            // 匹配表注释
            if (schema.getTableComment() != null &&
                    keywords.stream().anyMatch(k -> schema.getTableComment().toLowerCase().contains(k))) {
                matched = true;
            }

            // 匹配字段名或注释
            for (ColumnInfo col : schema.getColumns()) {
                if (keywords.stream().anyMatch(k -> col.getColumnName().toLowerCase().contains(k))) {
                    matched = true;
                    break;
                }
                if (col.getComment() != null &&
                        keywords.stream().anyMatch(k -> col.getComment().toLowerCase().contains(k))) {
                    matched = true;
                    break;
                }
            }

            if (matched) {
                relevant.add(schema);
            }
        }

        // 如果没有匹配到，返回所有表
        if (relevant.isEmpty()) {
            relevant.addAll(schemaCache.values());
        }

        return relevant;
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        log.info("清除Schema缓存");
        schemaCache.clear();
        lastUpdateTime = 0;
    }

    /**
     * 获取缓存统计信息
     */
    public Map<String, Object> getCacheStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("cachedTables", schemaCache.size());
        stats.put("lastUpdateTime", new java.util.Date(lastUpdateTime));
        stats.put("cacheExpired", needRefreshCache());
        return stats;
    }
}
