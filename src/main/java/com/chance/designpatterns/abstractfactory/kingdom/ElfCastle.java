package com.chance.designpatterns.abstractfactory.kingdom;

/**
 * 精灵城堡实现类
 * 该类实现了{@link Castle}接口，具体描述了精灵城堡
 *
 * @author chance
 * @date 2024/12/2 15:01
 * @since 1.0
 */
public class ElfCastle implements Castle {

    /**
     * 精灵城堡的描述信息
     */
    static final String DESCRIPTION = "This is the Elven castle!";

    /**
     * 获取精灵城堡的描述信息
     *
     * @return 精灵城堡的描述信息
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
