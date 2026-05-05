package com.chance.service;

import org.springframework.stereotype.Component;

/**
 * @author chance
 * @date 2025/12/9 08:37
 * @since 1.0
 */
@Component
public class StandardOrderProcessor extends AbstractOrderProcessor {

    @Override
    protected boolean validateOrder(String orderId) {
        System.out.println(" [标准] 正在进行用户权限和库存的常规校验...");
        // 假设校验通过
        return true;
    }

    @Override
    protected void updateInventory(String orderId) {
        System.out.println(" [标准] 正在调用库存系统扣减商品数量...");
    }

    // 钩子方法：选择性重写
    // @Override
    // protected void notifyUser(String orderId) { ... }
}
