package com.chance.designpatterns.strategy;

import com.chance.designpatterns.anticorruptionlayer.PaymentRequest;

/**
 * 支付策略接口
 *
 * @author chance
 * @date 2025/12/4 17:03
 * @since 1.0
 */
public interface PaymentStrategy {

    /**
     * 返回该策略处理的支付类型（例如：WECHAT_PAY, ALI_PAY）
     *
     * @return 支付类型
     */
    String getType();

    /**
     * 核心业务方法，所有支付处理都要实现这个契约
     *
     * @param request 支付请求
     */
    void handlePayment(PaymentRequest request);
}
