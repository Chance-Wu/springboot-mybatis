package com.chance.designpatterns.bridge;

/**
 * 定义支付模式的接口
 * 该接口用于规范支付操作的方法，实现支付功能的抽象
 * 主要作用是让不同的支付方式有一个统一的操作标准，而具体实现则由实现类来完成
 *
 * @author chance
 * @date 2024/12/26 14:51
 * @since 1.0
 */
public interface PaymentMode {

    /**
     * 执行支付操作的方法
     * 具体的支付行为由实现该接口的类来完成
     */
    void pay();
}
