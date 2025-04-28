package com.chance.designpatterns.abstractfactory.database;

/**
 * MySQL结果集
 * <p>该类实现了{@link ResultSet}接口，专门用于处理MySQL数据库的结果集
 * 它提供了处理MySQL结果集的具体实现
 *
 * @author chance
 * @date 2024/12/3 10:18
 * @since 1.0
 */
public class MySQLResultSet implements ResultSet {

    /**
     * 处理MySQL结果集的方法
     * 当调用此方法时，它会输出一条消息，表明正在处理MySQL结果集
     * 这个方法重写了ResultSet接口中的process方法，提供了具体的处理逻辑
     */
    @Override
    public void process() {
        System.out.println("Processing MySQL result set.");
    }
}
