package com.chance.designpatterns.abstractdocument.domain;

import com.chance.designpatterns.abstractdocument.Document;
import com.chance.designpatterns.abstractdocument.domain.enums.CarProperty;

import java.util.Optional;

/**
 * 定义了一个具有模型的文档接口
 *
 * <p>该接口继承自Document，并提供了一种获取模型信息的通用方法
 *
 * @author chance
 * @date 2024/11/28 14:53
 * @since 1.0
 */
public interface HasModel extends Document {

    default Optional<String> getModel() {
        return Optional.ofNullable((String) get(CarProperty.MODEL.toString()));
    }
}
