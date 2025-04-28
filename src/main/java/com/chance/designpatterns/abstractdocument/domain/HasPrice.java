package com.chance.designpatterns.abstractdocument.domain;

import com.chance.designpatterns.abstractdocument.Document;
import com.chance.designpatterns.abstractdocument.domain.enums.CarProperty;

import java.util.Optional;

/**
 * 定义了一个具有价格的文档接口
 *
 * <p>该接口继承自Document，并提供了一种获取价格信息的通用方法
 *
 * @author chance
 * @date 2024/11/28 14:53
 * @since 1.0
 */
public interface HasPrice extends Document {

    default Optional<Number> getPrice() {
        return Optional.ofNullable((Number) get(CarProperty.PRICE.toString()));
    }
}
