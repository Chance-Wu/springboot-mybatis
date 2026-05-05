package com.chance.component.ai;

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
import java.util.List;
import java.util.Map;

/**
 * 数据库Schema提取器
 * 从MySQL数据库中提取表结构信息，用于AI生成SQL
 *
 * @author chance
 * @date 2026/5/4 16:10
 * @since 1.0
 */
@Slf4j
@Component
public class DatabaseSchemaExtractor {

    @Resource
    private DataSource dataSource;

    /**
     * 获取所有表的Schema信息
     *
     * @return 格式化的Schema信息字符串
     */
    public String extractAllTablesSchema() {
        log.info("开始提取数据库Schema信息");

        StringBuilder schema = new StringBuilder();

        try (Connection conn = dataSource.getConnection()) {
            // 获取所有表
            List<String> tables = getAllTables(conn);

            schema.append("数据库包含以下 ").append(tables.size()).append(" 个表：\n\n");

            // 获取每个表的详细信息
            for (String tableName : tables) {
                String tableSchema = getTableSchema(conn, tableName);
                schema.append(tableSchema).append("\n\n");
            }

            log.info("Schema提取完成，共 {} 个表", tables.size());

        } catch (SQLException e) {
            log.error("提取Schema失败", e);
            throw new RuntimeException("提取数据库Schema失败", e);
        }

        return schema.toString();
    }

    /**
     * 获取指定表的Schema信息
     *
     * @param tableName 表名
     * @return 格式化的Schema信息
     */
    public String extractTableSchema(String tableName) {
        log.info("提取表 {} 的Schema信息", tableName);

        try (Connection conn = dataSource.getConnection()) {
            return getTableSchema(conn, tableName);
        } catch (SQLException e) {
            log.error("提取表Schema失败: {}", tableName, e);
            throw new RuntimeException("提取表Schema失败: " + tableName, e);
        }
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
     * 获取单个表的详细Schema
     */
    private String getTableSchema(Connection conn, String tableName) throws SQLException {
        StringBuilder schema = new StringBuilder();

        schema.append("表名: ").append(tableName).append("\n");

        // 获取列信息
        List<Map<String, Object>> columns = getColumns(conn, tableName);
        schema.append("字段:\n");

        for (Map<String, Object> column : columns) {
            schema.append(String.format("  - %s (%s)",
                    column.get("COLUMN_NAME"),
                    column.get("TYPE_NAME")));

            if ("YES".equals(column.get("IS_NULLABLE"))) {
                schema.append(", NULL");
            } else {
                schema.append(", NOT NULL");
            }

            if (column.get("COLUMN_DEF") != null) {
                schema.append(", DEFAULT: ").append(column.get("COLUMN_DEF"));
            }

            if ("PRI".equals(column.get("KEY"))) {
                schema.append(", PRIMARY KEY");
            }

            if ("auto_increment".equals(column.get("EXTRA"))) {
                schema.append(", AUTO_INCREMENT");
            }

            if (column.get("COLUMN_COMMENT") != null && !((String) column.get("COLUMN_COMMENT")).isEmpty()) {
                schema.append(", 注释: ").append(column.get("COLUMN_COMMENT"));
            }

            schema.append("\n");
        }

        // 获取索引信息
        List<Map<String, Object>> indexes = getIndexes(conn, tableName);
        if (!indexes.isEmpty()) {
            schema.append("索引:\n");
            for (Map<String, Object> index : indexes) {
                schema.append(String.format("  - %s (%s): %s\n",
                        index.get("INDEX_NAME"),
                        index.get("NON_UNIQUE").equals(0) ? "UNIQUE" : "INDEX",
                        index.get("COLUMN_NAME")));
            }
        }

        // 获取表注释
        String tableComment = getTableComment(conn, tableName);
        if (tableComment != null && !tableComment.isEmpty()) {
            schema.append("表注释: ").append(tableComment).append("\n");
        }

        return schema.toString();
    }

    /**
     * 获取表的列信息
     */
    private List<Map<String, Object>> getColumns(Connection conn, String tableName) throws SQLException {
        List<Map<String, Object>> columns = new ArrayList<>();

        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getColumns(conn.getCatalog(), null, tableName, "%");

        while (rs.next()) {
            Map<String, Object> column = new HashMap<>();
            column.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
            column.put("TYPE_NAME", rs.getString("TYPE_NAME"));
            column.put("COLUMN_SIZE", rs.getInt("COLUMN_SIZE"));
            column.put("IS_NULLABLE", rs.getString("IS_NULLABLE"));
            column.put("COLUMN_DEF", rs.getString("COLUMN_DEF"));
            column.put("REMARKS", rs.getString("REMARKS"));
            columns.add(column);
        }
        rs.close();

        // 获取主键信息
        rs = metaData.getPrimaryKeys(conn.getCatalog(), null, tableName);
        String primaryKey = null;
        if (rs.next()) {
            primaryKey = rs.getString("COLUMN_NAME");
        }
        rs.close();

        // 标记主键
        for (Map<String, Object> column : columns) {
            if (column.get("COLUMN_NAME").equals(primaryKey)) {
                column.put("KEY", "PRI");
            }
        }

        // 获取AUTO_INCREMENT信息
        try (Statement stmt = conn.createStatement();
             ResultSet infoRs = stmt.executeQuery("SHOW COLUMNS FROM " + tableName)) {

            Map<String, String> extraInfo = new HashMap<>();
            while (infoRs.next()) {
                extraInfo.put(infoRs.getString("Field"), infoRs.getString("Extra"));
            }

            for (Map<String, Object> column : columns) {
                String columnName = (String) column.get("COLUMN_NAME");
                if (extraInfo.containsKey(columnName)) {
                    column.put("EXTRA", extraInfo.get(columnName));
                }
            }
        }

        return columns;
    }

    /**
     * 获取表的索引信息
     */
    private List<Map<String, Object>> getIndexes(Connection conn, String tableName) throws SQLException {
        List<Map<String, Object>> indexes = new ArrayList<>();

        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getIndexInfo(conn.getCatalog(), null, tableName, false, false);

        while (rs.next()) {
            Map<String, Object> index = new HashMap<>();
            index.put("INDEX_NAME", rs.getString("INDEX_NAME"));
            index.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
            index.put("NON_UNIQUE", rs.getInt("NON_UNIQUE"));
            indexes.add(index);
        }
        rs.close();

        return indexes;
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
     * 获取示例数据（用于AI理解数据结构）
     *
     * @param tableName 表名
     * @param limit     限制条数
     * @return 示例数据
     */
    public String getSampleData(String tableName, int limit) {
        log.info("获取表 {} 的示例数据", tableName);

        StringBuilder sample = new StringBuilder();
        sample.append("表 ").append(tableName).append(" 的示例数据（前").append(limit).append("条）：\n");

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " LIMIT " + limit)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // 输出列名
            for (int i = 1; i <= columnCount; i++) {
                sample.append(metaData.getColumnName(i));
                if (i < columnCount) sample.append(" | ");
            }
            sample.append("\n");

            // 输出分隔线
            for (int i = 0; i < 80; i++) {
                sample.append("-");
            }
            sample.append("\n");

            // 输出数据
            int rowCount = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    Object value = rs.getObject(i);
                    sample.append(value != null ? value.toString() : "NULL");
                    if (i < columnCount) sample.append(" | ");
                }
                sample.append("\n");
                rowCount++;
            }

            sample.append("\n共 ").append(rowCount).append(" 条记录\n");

        } catch (SQLException e) {
            log.error("获取示例数据失败: {}", tableName, e);
            return "无法获取示例数据";
        }

        return sample.toString();
    }
}
