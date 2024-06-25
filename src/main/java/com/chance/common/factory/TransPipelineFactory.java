package com.chance.common.factory;

import com.chance.common.invoker.TransProcessPipelineInvoker;

/**
 * 转换流水线工厂接口，用于创建特定类型的转换处理流水线。
 * 该工厂的目的是通过传入不同的请求实体，动态地构建相应的转换处理流水线。
 *
 * @param <T> 泛型参数，表示流水线处理的请求实体类型。
 * @author: chance
 * @date: 2024/6/21 16:38
 * @since: 1.0
 */
public interface TransPipelineFactory<T> {

    /**
     * 创建流水线
     *
     * @param obj 请求实体
     * @return 转换流水调用器
     */
    TransProcessPipelineInvoker build(T obj);
}
