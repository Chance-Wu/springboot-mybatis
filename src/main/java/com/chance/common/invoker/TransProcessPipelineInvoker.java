package com.chance.common.invoker;

import com.chance.common.context.TransHandlerContext;

/**
 * 转换处理流水调用接口
 * 提供启动、终止交易处理流程以及获取上下文信息的方法。
 *
 * @author: chance
 * @date: 2024/6/21 15:21
 * @since: 1.0
 */
public interface TransProcessPipelineInvoker {

    /**
     * 启动流程
     */
    void start();

    /**
     * 终止流程
     */
    void shutDown();

    /**
     * 用于获取返回值
     *
     * @return 取得上下文
     */
    <T extends TransHandlerContext> T getContext();

}
