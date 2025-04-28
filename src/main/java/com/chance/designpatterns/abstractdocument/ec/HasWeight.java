package com.chance.designpatterns.abstractdocument.ec;

import java.util.Optional;

/**
 * 包含重量的接口
 * <p>该接口扩展了{@link Goods}接口，为商品添加了重量的属性和获取方法
 * 主要用于那些需要明确重量信息的商品
 *
 * @author chance
 * @date 2024/12/2 10:36
 * @since 1.0
 */
public interface HasWeight extends Goods {

    /**
     * 定义重量属性的键
     */
    String WEIGHT_PROPERTIES = "weight";

    /**
     * 获取商品的重量
     * 该方法使用了Optional来包装返回值，以优雅地处理可能的空值情况
     * 如果商品没有设置重量属性，则返回Optional.empty()
     *
     * @return Optional<Number> 可能包含重量值的Optional对象，如果未设置重量则为Optional.empty()
     */
    default Optional<Number> getWeight() {
        return Optional.ofNullable((Number) getProperty(WEIGHT_PROPERTIES));
    }
}
