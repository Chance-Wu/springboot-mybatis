package com.chance.designpatterns.anticorruptionlayer;

/**
 * 外部系统API模拟
 *
 * @author chance
 * @date 2024/12/10 16:01
 * @since 1.0
 */
public class ExternalPaymentApi {

    public ExternalPaymentResponse processPayment(ExternalPaymentRequest request) {
        // 模拟外部系统的支付处理
        ExternalPaymentResponse response = new ExternalPaymentResponse();
        response.setTransactionId("TX123456789");
        response.setStatus(true); // 模拟支付成功
        response.setErrorMessage(null);
        return response;
    }
}
