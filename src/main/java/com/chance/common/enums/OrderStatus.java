package com.chance.common.enums;

/**
 * @author chance
 * @date 2025/12/5 13:47
 * @since 1.0
 */
public enum OrderStatus {

    PAID("PAID", "已支付") {
        @Override
        public boolean canCancel() {
            // 允许取消
            return true;
        }
    },
    PENDING("PENDING", "待处理") {
        @Override
        public boolean canCancel() {
            // 允许取消
            return false;
        }
    },
    SHIPPED("SHIPPED", "已发货") {
        @Override
        public boolean canCancel() {
            // 不允许取消
            return false;
        }
    },
    DELIVERED("DELIVERED", "已收货") {
        @Override
        public boolean canCancel() {
            // 不允许取消
            return false;
        }
    },
    CANCELLED("CANCELLED", "已取消") {
        @Override
        public boolean canCancel() {
            // 已经取消，不允许再次操作
            return false;
        }
    },
    ;
    private final String code;
    private final String message;

    OrderStatus(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static OrderStatus getByCode(String code) {
        for (OrderStatus value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 关键步骤：定义抽象方法
     */
    public abstract boolean canCancel();
}
