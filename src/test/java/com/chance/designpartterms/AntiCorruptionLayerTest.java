package com.chance.designpartterms;

import com.chance.designpatterns.anticorruptionlayer.AntiCorruptionLayerService;
import com.chance.designpatterns.anticorruptionlayer.AntiCorruptionLayerServiceImpl;
import com.chance.designpatterns.anticorruptionlayer.ExternalPaymentApi;
import com.chance.designpatterns.anticorruptionlayer.OrderService;
import org.junit.Test;

/**
 * @author chance
 * @date 2024/12/10 16:31
 * @since 1.0
 */
public class AntiCorruptionLayerTest {

    @Test
    public void test() {
        // 创建外部系统API和防腐层实例
        ExternalPaymentApi externalPaymentApi = new ExternalPaymentApi();
        AntiCorruptionLayerService antiCorruptionLayerService = new AntiCorruptionLayerServiceImpl(externalPaymentApi);

        // 创建订单服务
        OrderService orderService = new OrderService(antiCorruptionLayerService);

        // 模拟订单处理
        orderService.processOrder("ORDER123", 100.00, "USD");
    }
}
