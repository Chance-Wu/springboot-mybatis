package com.chance.designpatterns.abstractfactory.database;

/**
 * 抽象命令接口定义了执行查询的操作
 * <p>该接口的实现类需要提供具体的执行查询逻辑
 *
 * @author chance
 * @date 2024/12/3 10:13
 * @since 1.0
 */
public interface Command {

    /**
     * 执行数据库查询
     *
     * @param query 查询语句
     */
    void execute(String query);
}
