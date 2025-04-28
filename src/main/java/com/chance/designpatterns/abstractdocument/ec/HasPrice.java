package com.chance.designpatterns.abstractdocument.ec;

import java.util.Optional;

/**
 * 包含价格的接口
 * <p>该接口继承自{@link Goods}接口，并添加了获取价格的通用方法
 * 主要用于在抽象文档设计模式中，为可能具有价格属性的文档提供统一的操作接口
 *
 * @author chance
 * @date 2024/12/2 10:46
 * @since 1.0
 */
public interface HasPrice extends Goods {

    String PRICE_PROPERTIES = "price";

    default Optional<Number> getPrice() {
        return Optional.ofNullable((Number) getProperty(PRICE_PROPERTIES));
    }
}
