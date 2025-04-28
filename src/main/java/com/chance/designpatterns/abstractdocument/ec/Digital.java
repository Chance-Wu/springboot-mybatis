package com.chance.designpatterns.abstractdocument.ec;

import java.util.Map;

/**
 * 数码产品
 *
 * @author chance
 * @date 2024/12/2 13:01
 * @since 1.0
 */
public class Digital extends AbstractGoods implements HasType, HasBrand, HasPrice, HasWeight {

    /**
     * 构造函数，初始化商品属性
     *
     * @param properties 商品属性映射表，不能为空
     * @throws NullPointerException 如果提供的属性为null，则抛出空指针异常
     */
    public Digital(Map<String, Object> properties) {
        super(properties);
    }
}
