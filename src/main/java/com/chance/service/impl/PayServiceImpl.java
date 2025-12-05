package com.chance.service.impl;

import com.chance.designpatterns.anticorruptionlayer.PaymentRequest;
import com.chance.service.PayService;
import org.springframework.stereotype.Service;

/**
 * @author chance
 * @date 2025/12/4 16:49
 * @since 1.0
 */
@Service
public class PayServiceImpl implements PayService {

    @Override
    public void handlePayment(String paymentType, PaymentRequest request) {
        if ("WECHAT_PAY".equals(paymentType)) {
            // 执行微信支付的复杂逻辑 A...
        } else if ("ALI_PAY".equals(paymentType)) {
            // 执行支付宝支付的复杂逻辑 B...
        } else if ("BANK_CARD".equals(paymentType)) {
            // 执行银行卡支付的复杂逻辑 C...
        }
        // 当新增一种支付方式时，我们必须修改这段 if-else 链条，违背了开闭原则。
    }
}
