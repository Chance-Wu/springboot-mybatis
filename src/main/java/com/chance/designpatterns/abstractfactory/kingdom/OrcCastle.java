package com.chance.designpatterns.abstractfactory.kingdom;

/**
 * 兽人城堡实现类
 * 该类实现了{@link Castle}接口，具体描述了兽人城堡
 *
 * @author chance
 * @date 2024/12/2 16:38
 * @since 1.0
 */
public class OrcCastle implements Castle {

    /**
     * 兽人城堡的描述信息
     */
    static final String DESCRIPTION = "This is the Orc Castle!";

    /**
     * 获取兽人城堡的描述信息
     *
     * @return 兽人城堡的描述信息
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
