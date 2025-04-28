package com.chance.designpatterns.anticorruptionlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付结果模型
 *
 * @author chance
 * @date 2024/12/10 15:46
 * @since 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentResult {

    private String transactionId;

    private boolean success;

    private String message;
}
