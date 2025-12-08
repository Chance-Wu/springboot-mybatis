package com.chance.service.impl;

import com.chance.entity.Order;
import com.chance.service.Specification;

/**
 * 规约 B：判断订单是否处大于某个阈值
 *
 * @author chance
 * @date 2025/12/8 10:14
 * @since 1.0
 */
public class AmountGreaterThanSpecification implements Specification<Order> {

    /**
     * 阈值
     */
    private final double threshold;

    public AmountGreaterThanSpecification(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean isSatisfiedBy(Order order) {
        // 业务规则：订单金额必须大于阈值
        return order.getAmount() > threshold;
    }
}
