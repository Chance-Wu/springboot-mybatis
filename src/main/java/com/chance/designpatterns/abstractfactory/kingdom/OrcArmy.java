package com.chance.designpatterns.abstractfactory.kingdom;

/**
 * 兽人王国军队实现类
 * 该类实现了{@link Army}接口，用于创建和管理兽人王国的军队
 *
 * @author chance
 * @date 2024/12/2 15:02
 * @since 1.0
 */
public class OrcArmy implements Army {

    /**
     * 兽人军队的描述信息
     */
    static final String DESCRIPTION = "This is the Orc Army!";

    /**
     * 获取兽人军队的描述信息
     *
     * @return 返回兽人军队的描述信息
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
