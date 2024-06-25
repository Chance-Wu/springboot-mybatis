package com.chance.common.handler;

import com.chance.common.invoker.TransProcessPipelineInvoker;

/**
 * 转换流水线接口，继承自TransProcessPipelineInvoker，用于定义特定的转换处理流程。
 * 该接口主要提供向流水线中添加处理事件的能力，包括在头部和尾部添加处理事件。
 *
 * @author: chance
 * @date: 2024/6/21 15:19
 * @since: 1.0
 */
public interface TransPipeline extends TransProcessPipelineInvoker {

    /**
     * 将转换处理事件添加到头位置
     *
     * @param handlers 事件处理handler集合
     */
    void addFirst(TransHandler... handlers);

    /**
     * 将转换处理事件添加到尾为止
     *
     * @param handlers 事件处理handler集合
     */
    void addLast(TransHandler... handlers);
}
