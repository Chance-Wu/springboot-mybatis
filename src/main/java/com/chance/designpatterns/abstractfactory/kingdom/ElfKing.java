package com.chance.designpatterns.abstractfactory.kingdom;

/**
 * 精灵国王实现类
 * 该类实现了{@link King}接口，具体描述了精灵国王的特征
 *
 * @author chance
 * @date 2024/12/2 15:02
 * @since 1.0
 */
public class ElfKing implements King {

    /**
     * 精灵国王的描述信息
     */
    static final String DESCRIPTION = "This is the Elven king!";

    /**
     * 获取精灵国王的描述信息
     *
     * @return 返回精灵国王的描述信息
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
