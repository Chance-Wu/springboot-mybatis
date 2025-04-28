package com.chance.designpatterns.abstractfactory.kingdom;

/**
 * 工厂制造者类，用于创建具体的王国工厂
 * <p>此类实现了工厂方法模式，根据不同的类型创建不同的工厂实例
 *
 * @author chance
 * @date 2024/12/2 16:35
 * @since 1.0
 */
public class FactoryMaker {

    /**
     * 王国类型枚举，用于定义可以创建的王国工厂类型
     */
    public enum KingdomType {
        ELF, ORC
    }

    /**
     * 根据指定的王国类型创建并返回相应的王国工厂
     *
     * @param type 王国类型，用于决定创建哪种类型的王国工厂
     * @return 返回对应类型的王国工厂实例
     * @throws IllegalArgumentException 如果给定的王国类型不受支持，则抛出此异常
     */
    public static KingdomFactory makeFactory(KingdomType type) {
        KingdomFactory factory;
        switch (type) {
            case ELF:
                // 创建并返回精灵王国工厂实例
                factory = new ElfKingdomFactory();
                break;
            case ORC:
                // 创建并返回兽人王国工厂实例
                factory = new OrcKingdomFactory();
                break;
            default:
                // 如果王国类型不受支持，抛出异常
                throw new IllegalArgumentException("KingdomType not supported.");
        }
        return factory;
    }
}
