package com.chance.designpatterns.abstractdocument.ec;

import java.util.Optional;

/**
 * 包含类型的接口
 * <p>该接口继承自{@link Goods}接口，并添加了获取类型信息的方法
 * 主要用于需要类型信息的文档或对象
 *
 * @author chance
 * @date 2024/12/2 10:39
 * @since 1.0
 */
public interface HasType extends Goods {

    /**
     * 定义类型属性的键
     */
    String TYPE_PROPERTIES = "type";

    /**
     * 获取类型的默认实现方法
     * 通过getProperty方法获取类型属性，并使用Optional进行封装，以避免空指针异常
     *
     * @return Optional<String> 包含类型的Optional对象，可能为空
     */
    default Optional<String> getType() {
        return Optional.ofNullable((String) getProperty(TYPE_PROPERTIES));
    }
}
