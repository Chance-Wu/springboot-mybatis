package com.chance.designpatterns.strategy;

import com.chance.designpatterns.anticorruptionlayer.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 阿里支付策略
 *
 * @author chance
 * @date 2025/12/5 09:05
 * @since 1.0
 */
@Slf4j
@Component
public class AliPayStrategy implements PaymentStrategy {

    @Override
    public String getType() {
        // 支付宝的唯一标识 Key
        return "ALI_PAY";
    }

    @Override
    public void handlePayment(PaymentRequest request) {
        // 实际业务中，这里是复杂的签名验证、日志记录等支付宝特有逻辑
        log.info("--- 执行支付宝支付逻辑：[订单号: {}] ---", request.getOrderId());
    }
}
