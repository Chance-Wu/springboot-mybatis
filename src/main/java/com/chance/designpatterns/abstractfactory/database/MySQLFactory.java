package com.chance.designpatterns.abstractfactory.database;

/**
 * MySQL工厂
 * <p>用于创建数据库相关的对象，包括连接、命令和结果集
 * 提供了一个抽象层，使得客户端代码可以不依赖于具体的数据库实现
 *
 * @author chance
 * @date 2024/12/3 10:20
 * @since 1.0
 */
public class MySQLFactory implements DatabaseFactory {

    /**
     * 创建数据库连接对象
     *
     * @return Connection对象，表示数据库的连接
     */
    @Override
    public Connection createConnection() {
        return new MySQLConnection();
    }

    /**
     * 创建数据库命令对象
     *
     * @return Command对象，用于执行SQL命令
     */
    @Override
    public Command createCommand() {
        return new MySQLCommand();
    }

    /**
     * 创建结果集对象
     *
     * @return ResultSet对象，用于存储查询结果
     */
    @Override
    public ResultSet createResultSet() {
        return new MySQLResultSet();
    }
}
