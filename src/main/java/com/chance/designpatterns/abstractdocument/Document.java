package com.chance.designpatterns.abstractdocument;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Document接口定义了抽象文档的基本操作方法。
 *
 * <p>抽象文档是一种键值对集合，可以包含嵌套的子文档。
 * 该接口的主要用途是提供一个标准的方式来操作这些文档中的数据。
 *
 * @author chance
 * @date 2024/11/28 14:03
 * @since 1.0
 */
public interface Document {

    /**
     * 向文档中插入一个键值对。
     * 如果指定键已存在，该方法应替换旧的值。
     *
     * @param key   文档中的键，用于标识值。
     * @param value 与键关联的值。
     */
    void put(String key, Object value);

    /**
     * 根据键获取文档中的值。
     *
     * @param key 要检索的键。
     * @return 与键关联的值，如果键不存在，则返回null。
     */
    Object get(String key);

    /**
     * 获取指定键下所有子文档的流。
     * 该方法允许用户通过提供一个构造函数来转换每个子文档，
     * 这使得用户可以以一致和类型安全的方式处理子文档。
     *
     * @param key         子文档的键。
     * @param constructor 一个函数，用于将子文档（表示为键值对映射）转换为指定类型T的实例。
     * @param <T>         转换后子文档的类型。
     * @return 子文档的流，每个子文档都已转换为指定类型T的实例。
     */
    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}
