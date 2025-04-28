package com.chance.designpatterns.abstractdocument.domain;


import com.chance.designpatterns.abstractdocument.AbstractDocument;

import java.util.Map;

/**
 * Car 类表示一个特定的抽象文档实现，专注于汽车领域。
 * <p>它继承自 AbstractDocument，并实现了与汽车特性相关的接口，如型号、部件、价格和类型。
 * 这种设计允许 Car 类封装与汽车相关的数据和行为，同时提供灵活的属性管理结构。
 *
 * @author chance
 * @date 2024/11/28 14:59
 * @since 1.0
 */
public class Car extends AbstractDocument implements HasModel, HasParts, HasPrice, HasType {

    /**
     * 构造一个 Car 实例，使用属性集合进行初始化。
     *
     * @param properties 包含汽车属性的键值对映射，例如型号、部件、价格等。
     *                   该构造函数将属性直接传递给父类 AbstractDocument 进行初始化。
     */
    public Car(Map<String, Object> properties) {
        super(properties);
    }
}
