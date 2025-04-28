package com.chance.designpatterns.abstractfactory.database;

/**
 * MySQL命令
 * <p>该类实现了{@link Command}接口，用于执行MySQL数据库的命令
 * * 主要功能是模拟执行数据库命令的操作
 *
 * @author chance
 * @date 2024/12/3 10:17
 * @since 1.0
 */
public class MySQLCommand implements Command {

    /**
     * 执行MySQL命令
     *
     * @param query 待执行的SQL查询语句
     */
    @Override
    public void execute(String query) {
        System.out.println("Executing MySQL command: " + query);
    }
}
