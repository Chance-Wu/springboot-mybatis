package com.chance.designpatterns.abstractdocument.ec;

import java.util.Map;

/**
 * 商品类别
 * <p>继承自{@link AbstractGoods}抽象类，并实现了HasType, HasBrand, HasPrice, HasWeight接口
 * 该类用于表示具有类型、品牌、价格和重量属性的商品类别
 *
 * @author chance
 * @date 2024/12/2 09:40
 * @since 1.0
 */
public class Category extends AbstractGoods implements HasType, HasBrand, HasPrice, HasWeight {

    /**
     * 构造方法，接收一个属性映射，并调用父类构造方法初始化
     *
     * @param properties 包含商品类别属性的映射，如类型、品牌、价格和重量等
     */
    protected Category(Map<String, Object> properties) {
        super(properties);
    }
}
