package com.chance.service.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 * 智能SQL生成助手（优化版 v3.0）
 * <p>
 * 核心功能：
 * 1. 自然语言转SQL - 支持多种查询场景
 * 2. SQL解释 - 通俗易懂的SQL说明
 * 3. SQL优化 - 性能优化建议
 * 4. SQL验证 - 语法和安全性检查
 * <p>
 * 设计原则：
 * - 动态Prompt：支持运行时配置系统指令和用户消息
 * - 类型安全：明确的参数和返回值类型
 * - 可扩展性：易于添加新的SQL相关功能
 *
 * @author chance
 * @date 2026/5/4 16:10
 * @since 3.0
 */
public interface SqlAssistant {

    /**
     * 根据自然语言生成SQL（核心方法）
     * <p>
     * 使用场景：
     * - 简单查询："查询所有用户"
     * - 条件查询："查询年龄大于18岁的用户"
     * - 聚合查询："统计每个年龄的用户数量"
     * - JOIN查询："查询每个用户的订单数量"
     *
     * @param systemInstruction 系统指令（定义AI角色、规则、输出格式）
     * @param prompt            用户提示（包含Schema信息、用户需求、示例等）
     * @return 生成的SQL语句（纯SQL，无解释）
     * @example systemInstruction: "你是一个MySQL专家..."
     * prompt: "【数据库结构】...\n【用户需求】查询活跃用户\n【生成的SQL】"
     * result: "SELECT id, username FROM user WHERE status = 1 LIMIT 100"
     */
    @SystemMessage("{{systemInstruction}}")
    @UserMessage("{{prompt}}")
    String generateSql(
            @V("systemInstruction") String systemInstruction,
            @V("prompt") String prompt);

    /**
     * 解释SQL语句
     * <p>
     * 使用场景：
     * - 理解复杂SQL的作用
     * - 学习SQL语法
     * - 向非技术人员解释
     *
     * @param systemInstruction 系统指令（定义解释风格、长度限制）
     * @param prompt            用户提示（包含待解释的SQL）
     * @return SQL的中文解释（简洁易懂，200字以内）
     * @example prompt: "请解释以下SQL：SELECT u.username, COUNT(o.id) FROM user u LEFT JOIN orders o..."
     * result: "这个SQL查询每个用户的订单数量。使用LEFT JOIN关联用户表和订单表..."
     */
    @SystemMessage("{{systemInstruction}}")
    @UserMessage("{{prompt}}")
    String explainSql(
            @V("systemInstruction") String systemInstruction,
            @V("prompt") String prompt);

    /**
     * 优化SQL语句
     * <p>
     * 使用场景：
     * - 提升查询性能
     * - 遵循最佳实践
     * - 修复潜在问题
     *
     * @param systemInstruction 系统指令（定义优化目标、策略）
     * @param prompt            用户提示（包含Schema、原始SQL）
     * @return 优化后的SQL（保持功能等价）
     * @example prompt: "【数据库结构】...\n【原始SQL】SELECT * FROM user WHERE YEAR(create_time) = 2024\n【优化后的SQL】"
     * result: "SELECT id, username, email FROM user WHERE create_time >= '2024-01-01' AND create_time < '2025-01-01'"
     */
    @SystemMessage("{{systemInstruction}}")
    @UserMessage("{{prompt}}")
    String optimizeSql(
            @V("systemInstruction") String systemInstruction,
            @V("prompt") String prompt);

    /**
     * 验证SQL语法和安全性
     * <p>
     * 使用场景：
     * - 执行前检查SQL
     * - 防止SQL注入
     * - 确保只读操作
     *
     * @param systemInstruction 系统指令（定义验证规则）
     * @param prompt            用户提示（包含待验证的SQL）
     * @return 验证结果（PASS/FAIL + 原因）
     * @example prompt: "请验证以下SQL的安全性和语法：DELETE FROM user WHERE id = 1"
     * result: "FAIL: 检测到危险操作DELETE，只允许SELECT查询"
     */
    @SystemMessage("{{systemInstruction}}")
    @UserMessage("{{prompt}}")
    String validateSql(
            @V("systemInstruction") String systemInstruction,
            @V("prompt") String prompt);

    /**
     * 生成SQL注释
     * <p>
     * 使用场景：
     * - 为SQL添加文档
     * - 提高代码可读性
     * - 团队协作
     *
     * @param systemInstruction 系统指令（定义注释格式）
     * @param prompt            用户提示（包含SQL和业务说明）
     * @return 带注释的SQL
     * @example prompt: "为以下SQL添加注释：SELECT id, username FROM user WHERE age > 18"
     * result: "-- 查询成年用户列表\nSELECT id, username -- 用户ID, 用户名\nFROM user\nWHERE age > 18 -- 年龄大于18岁"
     */
    @SystemMessage("{{systemInstruction}}")
    @UserMessage("{{prompt}}")
    String commentSql(
            @V("systemInstruction") String systemInstruction,
            @V("prompt") String prompt);
}
