package com.chance.designpatterns.abstractfactory.kingdom;

/**
 * 精灵王国工厂实现类
 * <p>该类负责创建精灵王国相关的实例，包括城堡、国王和军队
 * 通过实现KingdomFactory接口，具体实现了如何创建精灵王国的组成部分
 *
 * @author chance
 * @date 2024/12/2 16:37
 * @since 1.0
 */
public class OrcKingdomFactory implements KingdomFactory {

    /**
     * 创建城堡的方法
     * 该方法返回一个ElfCastle实例，代表精灵王国的城堡
     *
     * @return Castle的实现类ElfCastle的实例
     */
    @Override
    public Castle createCastle() {
        return new OrcCastle();
    }

    /**
     * 创建国王的方法
     * 该方法返回一个ElfKing实例，代表精灵王国的国王
     *
     * @return King的实现类ElfKing的实例
     */
    @Override
    public King createKing() {
        return new OrcKing();
    }

    /**
     * 创建军队的方法
     * 该方法返回一个ElfArmy实例，代表精灵王国的军队
     *
     * @return Army的实现类ElfArmy的实例
     */
    @Override
    public Army createArmy() {
        return new OrcArmy();
    }
}
