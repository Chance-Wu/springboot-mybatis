package com.chance.util;

import com.chance.common.enums.OrderStatus;

/**
 * @author chance
 * @date 2025/12/5 13:53
 * @since 1.0
 */
public class StatusUtils {

    public static boolean cancel(OrderStatus status) {
        switch (status) {
            case PAID:
            case PENDING:
                return true;
            case SHIPPED:
            case DELIVERED:
            case CANCELLED:
                return false;
            default:
                return false;
        }
    }
}
