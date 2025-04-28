package com.chance.designpatterns.abstractfactory.database;

/**
 * 抽象结果集接口
 * <p>该接口定义了处理结果集的标准方法，用于规范结果集的处理流程
 *
 * @author chance
 * @date 2024/12/3 10:14
 * @since 1.0
 */
public interface ResultSet {

    /**
     * 处理结果集的方法
     * 该方法需要由实现类具体实现，以完成特定的处理逻辑
     */
    void process();
}
