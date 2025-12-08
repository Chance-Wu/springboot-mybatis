package com.chance.service;

/**
 * 规约接口 - 用于实现业务规则的封装和组合
 * <p>
 * Specification模式（规约模式）是一种设计模式，它使用布尔型谓词来选择满足特定条件的对象。
 * 此接口定义了一个规约的基本结构，允许通过逻辑操作符（与、或、非）进行规约组合。
 * <p>
 * 使用示例：
 * Specification<Integer> greaterThanFive = n -> n > 5;
 * Specification<Integer> lessThanTen = n -> n < 10;
 * Specification<Integer> betweenFiveAndTen = greaterThanFive.and(lessThanTen);
 * boolean result = betweenFiveAndTen.isSatisfiedBy(7); // true
 *
 * @param <T> 泛型参数，表示需要检验的对象类型
 * @author chance
 * @date 2025/12/8 09:17
 * @since 1.0
 */
@FunctionalInterface
public interface Specification<T> {

    /**
     * 抽象方法：检查目标对象是否满足规约条件
     * <p>
     * 这是规约模式的核心方法，用于判断给定的对象是否符合当前规约定义的条件。
     * 实现类需要提供具体的判断逻辑。
     *
     * @param item 待检验的目标对象，不能为null
     * @return true表示对象满足规约条件，false表示不满足
     */
    boolean isSatisfiedBy(T item);

    /**
     * 默认方法：实现 AND 逻辑组合 (规约 A 且 规约 B)
     * <p>
     * 将当前规约与另一个规约进行逻辑与操作，生成一个新的复合规约。
     * 只有当对象同时满足两个规约时，新规约才返回true。
     *
     * @param other 需要组合的另一个规约，不能为null
     * @return 组合后的新规约，当且仅当两个规约都满足时返回true
     */
    default Specification<T> and(Specification<T> other) {
        return item -> isSatisfiedBy(item) && other.isSatisfiedBy(item);
    }

    /**
     * 默认方法：实现 OR 逻辑组合 (规约 A 或 规约 B)
     * <p>
     * 将当前规约与另一个规约进行逻辑或操作，生成一个新的复合规约。
     * 当对象满足任意一个规约时，新规约就返回true。
     *
     * @param other 需要组合的另一个规约，不能为null
     * @return 组合后的新规约，当任一规约满足时返回true
     */
    default Specification<T> or(Specification<T> other) {
        return item -> isSatisfiedBy(item) || other.isSatisfiedBy(item);
    }

    /**
     * 默认方法：实现 NOT 逻辑（规约 A 的非）
     * <p>
     * 对当前规约进行逻辑非操作，生成一个新的规约。
     * 当对象满足原规约时，新规则返回false；反之则返回true。
     *
     * @return 取反后的新规约
     */
    default Specification<T> not() {
        return item -> !isSatisfiedBy(item);
    }
}
