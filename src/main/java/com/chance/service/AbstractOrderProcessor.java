package com.chance.service;

import com.chance.common.ErrorCodeEnum;
import com.chance.common.exception.BizException;

/**
 * @author chance
 * @date 2025/12/8 16:28
 * @since 1.0
 */
public abstract class AbstractOrderProcessor {

    /**
     * 模板方法：定义流程骨架（）
     *
     * @param orderId
     */
    public final void processOrder(String orderId) {
        System.out.println("--- 开始处理订单: " + orderId + " ---");

        // 步骤 1：抽象方法，强制子类实现
        if (!validateOrder(orderId)) {
            throw new BizException(ErrorCodeEnum.FAIL, "订单验证失败");
        }

        // 步骤 2：具体方法，通用逻辑
        deductMoney(orderId);

        // 步骤 3：抽象方法，强制字类实现
        updateInventory(orderId);

        // 步骤 4：钩子方法，字类可选择性重写
        notifyUser(orderId);

        System.out.println("--- 订单处理完成 ---");
    }

    /**
     * 订单验证
     * 抽象基本方法，由子类实现
     *
     * @param orderId 订单ID
     * @return 订单验证结果
     */
    protected abstract boolean validateOrder(String orderId);

    /**
     * 更新库存
     * 抽象基本方法，由子类实现
     *
     * @param orderId 订单ID
     */
    protected abstract void updateInventory(String orderId);

    /**
     * 具体方法（通用逻辑）
     *
     * @param orderId 订单ID
     */
    private void deductMoney(String orderId) {
        System.out.println(" [通用] 正在进行扣款操作...");
    }

    /**
     * 钩子方法 (Hook Method)：提供默认实现，子类可选择重写
     *
     * @param orderId
     */
    protected void notifyUser(String orderId) {
        System.out.println(" [默认] 发送通用邮件通知...");
    }
}
