package com.chance.designpatterns.abstractfactory.database;

/**
 * PostgreSQL结果集
 * <p>该类实现了{@link ResultSet}接口，用于处理来自PostgreSQL数据库的查询结果
 * 主要功能包括对结果集的处理等
 *
 * @author chance
 * @date 2024/12/3 10:19
 * @since 1.0
 */
public class PostgreSQLResultSet implements ResultSet {

    /**
     * 处理结果集的方法
     * 当执行一个SQL查询后，使用此方法来处理得到的结果集
     * 目前该方法仅打印处理信息，具体逻辑可能在后续版本中实现
     */
    @Override
    public void process() {
        System.out.println("Processing PostgreSQL result set.");
    }
}
