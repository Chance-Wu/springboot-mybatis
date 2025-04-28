package com.chance.designpatterns.adapter.object;

/**
 * 目标接口
 * 定义支付网关的标准接口，用于进行支付操作
 * 该接口未来可能包含其他与支付相关的操作或规范
 *
 * @author chance
 * @date 2024/12/6 16:43
 * @since 1.0
 */
public interface PaymentGateway {

    /**
     * 执行支付操作
     *
     * @param amount 支付金额，单位为元
     */
    void pay(double amount);
}
