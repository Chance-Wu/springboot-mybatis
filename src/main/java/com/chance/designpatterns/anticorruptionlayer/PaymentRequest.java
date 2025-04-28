package com.chance.designpatterns.anticorruptionlayer;

import lombok.Data;

/**
 * 支付请求模型
 *
 * @author chance
 * @date 2024/12/10 15:43
 * @since 1.0
 */
@Data
public class PaymentRequest {

    private String orderId;

    private double amount;

    private String currency;
}
