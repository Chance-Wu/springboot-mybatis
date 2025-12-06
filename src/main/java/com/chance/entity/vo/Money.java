package com.chance.entity.vo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * DDD 消除“基本类型痴迷”，值对象
 *
 * @author chance
 * @date 2025/12/6 09:14
 * @since 1.0
 */
public class Money {

    private final BigDecimal amount;
    private final String currencyCode;

    public Money(BigDecimal amount, String currencyCode) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("金额不能为负数");
        }
        // ... 其他校验
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    // 确保 Money 对象是不可变的
    // ... 只有 Getter，没有 Setter ...

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }


    // 重写 equals() 和 hashCode()，确保只要属性相同，对象就相等
    // ...

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount) && Objects.equals(currencyCode, money.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currencyCode);
    }
}
