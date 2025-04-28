package com.chance.designpatterns.abstractfactory.kingdom;

/**
 * 王国工厂抽象接口
 * <p>该接口用于创建不同类型的王国对象，包括城堡、国王和军队
 * 通过抽象工厂模式，确保同一工厂生产的产品属于同一类别（或风格）
 *
 * @author chance
 * @date 2024/12/2 15:03
 * @since 1.0
 */
public interface KingdomFactory {

    /**
     * 创建城堡的方法
     *
     * @return Castle 实例，代表一个特定类型的城堡
     */
    Castle createCastle();

    /**
     * 创建国王的方法
     *
     * @return King 实例，代表一个特定类型的国王
     */
    King createKing();

    /**
     * 创建军队的方法
     *
     * @return Army 实例，代表一个特定类型的军队
     */
    Army createArmy();
}
