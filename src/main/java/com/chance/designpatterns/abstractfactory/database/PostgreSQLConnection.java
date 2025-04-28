package com.chance.designpatterns.abstractfactory.database;

/**
 * PostgreSQL连接
 * <p>实现了{@link Connection}接口，具体负责PostgreSQL数据库的连接和关闭
 *
 * @author chance
 * @date 2024/12/3 10:16
 * @since 1.0
 */
public class PostgreSQLConnection implements Connection {

    /**
     * 打开PostgreSQL数据库连接
     * 当需要与PostgreSQL数据库建立连接时调用此方法
     */
    @Override
    public void open() {
        System.out.println("Opening a PostgreSQL connection.");
    }

    /**
     * 关闭PostgreSQL数据库连接
     * 当完成对PostgreSQL数据库的操作，释放资源时调用此方法
     */
    @Override
    public void close() {
        System.out.println("Closing a PostgreSQL connection.");
    }
}
