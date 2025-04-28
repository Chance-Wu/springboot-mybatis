package com.chance.designpatterns.abstractfactory.database;

/**
 * PostgreSQL命令
 * <p>实现了{@link Command}接口，用于执行PostgreSQL数据库命令
 * 该类提供了与PostgreSQL数据库交互的具体实现
 *
 * @author chance
 * @date 2024/12/3 10:18
 * @since 1.0
 */
public class PostgreSQLCommand implements Command {

    /**
     * 执行PostgreSQL命令的方法
     * 接受一个查询字符串作为参数，并打印执行命令的消息
     *
     * @param query 查询字符串，包含具体的数据库操作指令
     */
    @Override
    public void execute(String query) {
        System.out.println("Executing PostgreSQL command: " + query);
    }
}
