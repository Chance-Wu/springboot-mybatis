package com.chance.designpatterns.strategy;

import com.chance.designpatterns.anticorruptionlayer.PaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 微信支付策略
 *
 * @author chance
 * @date 2025/12/5 09:08
 * @since 1.0
 */
@Slf4j
@Component
public class WeChatPayStrategy implements PaymentStrategy {
    @Override
    public String getType() {
        // 微信支付的唯一标识 Key
        return "WECHAT_PAY";
    }

    @Override
    public void handlePayment(PaymentRequest request) {
        // 实际业务中，这里是微信支付特有的通知和对账逻辑
        log.info("--- 执行微信支付逻辑：[订单号: {}] ---", request.getOrderId());
    }
}
