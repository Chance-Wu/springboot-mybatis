package com.chance.service;

import com.chance.designpatterns.anticorruptionlayer.PaymentRequest;

/**
 * @author chance
 * @date 2025/12/4 16:48
 * @since 1.0
 */
public interface PayService {

    void handlePayment(String paymentType, PaymentRequest request);
}
