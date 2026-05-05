package com.chance.service.impl;

import com.chance.service.OrderService;
import com.chance.service.StandardOrderProcessor;
import org.springframework.stereotype.Service;

/**
 * @author chance
 * @date 2025/12/9 09:33
 * @since 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final StandardOrderProcessor standardOrderProcessor;

    public OrderServiceImpl(StandardOrderProcessor standardOrderProcessor) {
        this.standardOrderProcessor = standardOrderProcessor;
    }

    @Override
    public void handleNewOrder(String orderId) {

        // 调用模板方法：流程骨架启动，将依次执行 validateOrder -> deductMoney -> updateInventory -> notifyUser
        standardOrderProcessor.processOrder(orderId);

        System.out.println("订单 " + orderId + " 已进入下一个流程。");
    }
}
