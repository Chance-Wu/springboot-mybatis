package com.chance.designpatterns.abstractdocument.ec;

import java.util.Optional;

/**
 * 包含品牌的接口
 * <p>该接口继承自{@link Goods}接口，并添加了获取品牌信息的方法
 * 主要用于在抽象文档设计模式中处理与品牌相关的属性
 *
 * @author chance
 * @date 2024/12/2 10:55
 * @since 1.0
 */
public interface HasBrand extends Goods {

    /**
     * 定义品牌属性的键
     */
    String BRAND_PROPERTIES = "brand";

    /**
     * 获取品牌信息的方法
     * 该方法利用Optional类优雅地处理可能为null的品牌属性值
     * 避免了直接返回null可能引起的NullPointerException
     *
     * @return 返回一个Optional包装的品牌名称字符串，如果品牌属性为null，则返回一个空的Optional对象
     */
    default Optional<String> getBrand() {
        return Optional.ofNullable((String) getProperty(BRAND_PROPERTIES));
    }
}
