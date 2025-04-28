package com.chance.designpatterns.anticorruptionlayer;

import lombok.Data;

/**
 * 外部支付模型
 *
 * @author chance
 * @date 2024/12/10 15:48
 * @since 1.0
 */
@Data
public class ExternalPaymentRequest {

    private String externalOrderId;

    private double totalAmount;

    private String currencyCode;
}
