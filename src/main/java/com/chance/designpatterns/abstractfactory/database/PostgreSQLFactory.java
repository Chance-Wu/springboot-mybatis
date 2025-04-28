package com.chance.designpatterns.abstractfactory.database;

/**
 * PostgreSQL工厂
 * <p>该类实现了{@link DatabaseFactory}接口，专门用于创建与PostgreSQL数据库相关的对象
 *
 * @author chance
 * @date 2024/12/3 10:21
 * @since 1.0
 */
public class PostgreSQLFactory implements DatabaseFactory {

    /**
     * 创建并返回一个PostgreSQL数据库连接对象
     *
     * @return Connection 实现了Connection接口的PostgreSQL数据库连接对象
     */
    @Override
    public Connection createConnection() {
        return new PostgreSQLConnection();
    }

    /**
     * 创建并返回一个PostgreSQL数据库命令对象
     *
     * @return Command 实现了Command接口的PostgreSQL数据库命令对象
     */
    @Override
    public Command createCommand() {
        return new PostgreSQLCommand();
    }

    /**
     * 创建并返回一个PostgreSQL数据库结果集对象
     *
     * @return ResultSet 实现了ResultSet接口的PostgreSQL数据库结果集对象
     */
    @Override
    public ResultSet createResultSet() {
        return new PostgreSQLResultSet();
    }
}
