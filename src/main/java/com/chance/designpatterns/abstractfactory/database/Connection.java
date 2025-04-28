package com.chance.designpatterns.abstractfactory.database;

/**
 * 定义抽象连接，用于建立和数据库的连接
 * <p>此接口不依赖于具体的数据库类型，提供了统一的连接操作方法
 *
 * @author chance
 * @date 2024/12/3 10:13
 * @since 1.0
 */
public interface Connection {

    /**
     * 打开数据库连接的方法
     * 通过此方法初始化与数据库的通信链路
     */
    void open();

    /**
     * 关闭数据库连接的方法
     * 调用此方法以释放数据库资源并断开通信链路
     */
    void close();

}
