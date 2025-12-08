package com.chance;

import com.chance.entity.Order;
import com.chance.service.Specification;
import com.chance.service.impl.AmountGreaterThanSpecification;
import com.chance.service.impl.IsActiveOrderSpecification;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author chance
 * @date 2025/12/8 10:20
 * @since 1.0
 */
@SpringBootTest
public class SpecificationTest {

    @Test
    public void test() {
        Order order1 = new Order(1, 1500.0, true);   // 活跃, 金额高 -> 满足
        Order order2 = new Order(2, 500.0, true);    // 活跃, 金额低 -> 不满足 AND 组合
        Order order3 = new Order(3, 2000.0, false);  // 不活跃, 金额高 -> 不满足 AND 组合

        // 1.定义基本规则实例
        Specification<Order> isActive = new IsActiveOrderSpecification();
        Specification<Order> isLargeAmount = new AmountGreaterThanSpecification(1000.0);

        // 2. 优雅组合： 订单必须活跃 且 金额大于 1000
        Specification<Order> eligibleOrderSpec = isActive.and(isLargeAmount);

        System.out.println("--- AND 组合 ---");
        System.out.println("Order 1 (活跃, 1500) 满足? " + eligibleOrderSpec.isSatisfiedBy(order1)); // true
        System.out.println("Order 2 (活跃, 500) 满足? " + eligibleOrderSpec.isSatisfiedBy(order2));  // false
        System.out.println("Order 3 (不活跃, 2000) 满足? " + eligibleOrderSpec.isSatisfiedBy(order3)); // false

        // 3. 复杂组合示例：订单必须是不活跃 OR 金额大于 1000
        Specification<Order> complexSpec = isActive.not().or(isLargeAmount);

        System.out.println("\n--- 复杂 OR 组合 (不活跃 OR 金额高) ---");
        System.out.println("Order 1 (活跃, 1500) 满足? " + complexSpec.isSatisfiedBy(order1)); // true (金额高满足)
        System.out.println("Order 3 (不活跃, 2000) 满足? " + complexSpec.isSatisfiedBy(order3)); // true (不活跃满足)
    }
}
