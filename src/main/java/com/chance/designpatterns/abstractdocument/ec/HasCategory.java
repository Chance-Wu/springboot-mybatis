package com.chance.designpatterns.abstractdocument.ec;

import java.util.stream.Stream;

/**
 * 包含类别的接口
 * <p>该接口继承自{@link Goods}接口，并添加了获取商品类别信息的方法
 * 主要用于获取商品的类别，以确保在商品文档中可以包含类别信息
 *
 * @author chance
 * @date 2024/12/2 10:49
 * @since 1.0
 */
public interface HasCategory extends Goods {
    /**
     * 定义类别属性名称
     */
    String CATEGORY_PROPERTIES = "category";

    /**
     * 获取商品类别信息的默认方法
     * <p>通过调用此方法，可以获取到商品的类别信息流
     *
     * @return 商品类别信息流
     */
    default Stream<Category> getCategory() {
        return children(CATEGORY_PROPERTIES, Category::new);
    }
}
