package com.chance.designpatterns.abstractdocument.ec;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 商品接口
 * <p>定义了商品对象的基本操作，包括获取属性、设置属性以及获取子商品集合
 *
 * @author chance
 * @date 2024/12/2 10:08
 * @since 1.0
 */
public interface Goods {

    /**
     * 获取商品的指定属性
     *
     * @param key 属性的键
     * @return 属性的值
     */
    Object getProperty(String key);

    /**
     * 在商品中添加或更新一个属性
     *
     * @param key   属性的键
     * @param value 属性的值
     */
    void putProperties(String key, Object value);

    /**
     * 获取指定类型的子商品集合
     * 通过提供一个键和一个构造函数，将子商品数据转换为指定类型对象的集合
     *
     * @param <T>         子商品集合的类型
     * @param key         子商品集合的键
     * @param constructor 将子商品数据转换为指定类型对象的构造函数
     * @return 子商品集合的流
     */
    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}
