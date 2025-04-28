package com.chance.designpatterns.abstractfactory.database;

/**
 * 数据库抽象工厂
 * 用于创建数据库相关的对象，包括连接、命令和结果集
 * 提供了一个抽象层，使得客户端代码可以不依赖于具体的数据库实现
 *
 * @author chance
 * @date 2024/12/3 10:10
 * @since 1.0
 */
public interface DatabaseFactory {


    /**
     * 创建数据库连接对象
     *
     * @return Connection对象，表示数据库的连接
     */
    Connection createConnection();

    /**
     * 创建数据库命令对象
     *
     * @return Command对象，用于执行SQL命令
     */
    Command createCommand();

    /**
     * 创建结果集对象
     *
     * @return ResultSet对象，用于存储查询结果
     */
    ResultSet createResultSet();
}
