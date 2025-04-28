package com.chance.designpatterns.anticorruptionlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 外部支付响应
 *
 * @author chance
 * @date 2024/12/10 15:52
 * @since 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExternalPaymentResponse {

    private String transactionId;

    private boolean status;

    private String errorMessage;
}
