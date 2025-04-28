package com.chance.designpatterns.abstractdocument.ec;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 抽象商品类
 *
 * @author chance
 * @date 2024/12/2 10:18
 * @since 1.0
 */
public class AbstractGoods implements Goods {

    /**
     * 存储商品属性的映射表
     */
    private final Map<String, Object> properties;

    /**
     * 构造函数，初始化商品属性
     *
     * @param properties 商品属性映射表，不能为空
     * @throws NullPointerException 如果提供的属性为null，则抛出空指针异常
     */
    public AbstractGoods(Map<String, Object> properties) {
        Objects.requireNonNull(properties, "属性不能为空");
        this.properties = properties;
    }

    /**
     * 获取指定键的属性值
     *
     * @param key 属性键
     * @return 对应键的属性值，如果键不存在则返回null
     */
    @Override
    public Object getProperty(String key) {
        return this.properties.get(key);
    }

    /**
     * 向属性映射表中添加或更新键值对
     *
     * @param key   属性键
     * @param value 属性值
     */
    @Override
    public void putProperties(String key, Object value) {
        this.properties.put(key, value);
    }

    /**
     * 获取指定键的子元素流
     *
     * @param key         子元素的键
     * @param constructor 将子元素映射为T类型的函数
     * @param <T>         子元素转换后的类型
     * @return 子元素流，如果键不存在或对应的值为空，则返回空流
     */
    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        Optional<List<Map<String, Object>>> any = Stream.of(getProperty(key))
                .filter(Objects::nonNull)// 过滤掉值为null的元素
                .map(el -> (List<Map<String, Object>>) el)// 将过滤后的元素转换为 List<Map<String, Object>> 类型
                .findAny();// 查找任意元素
        // 如果找到元素，将其转换为流并应用 constructor 函数；如果没有找到元素，返回空流
        return any.isPresent() ? any.get().stream().map(constructor) : Stream.empty();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this.properties);
    }
}
