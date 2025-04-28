package com.chance.designpatterns.abstractdocument.domain;


import com.chance.designpatterns.abstractdocument.AbstractDocument;

import java.util.Map;

/**
 * Part 类表示一个抽象文档的具体实现，封装了类型、型号和价格等属性。
 * <p>它继承自 AbstractDocument 并实现了 HasType、HasModel 和 HasPrice 接口，
 * 表明部件具有类型、型号和价格等属性。
 *
 * @author chance
 * @date 2024/11/28 16:32
 * @since 1.0
 */
public class Part extends AbstractDocument implements HasType, HasModel, HasPrice {

    /**
     * 构造一个具有指定属性的 Part 实例。
     *
     * @param properties 包含部件属性的映射，如类型、型号和价格。
     */
    public Part(Map<String, Object> properties) {
        super(properties);
    }
}
