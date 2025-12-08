package com.chance.service.impl;

import com.chance.entity.Order;
import com.chance.service.Specification;

/**
 * 规约 A：判断订单是否处于活跃状态
 *
 * @author chance
 * @date 2025/12/8 10:10
 * @since 1.0
 */
public class IsActiveOrderSpecification implements Specification<Order> {

    @Override
    public boolean isSatisfiedBy(Order order) {
        // 业务规则：订单必须处于活跃状态
        return order.isActive();
    }
}
