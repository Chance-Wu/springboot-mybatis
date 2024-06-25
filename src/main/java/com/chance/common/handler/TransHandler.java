package com.chance.common.handler;

import com.chance.common.context.TransHandlerContext;

/**
 * 转换处理器接口，定义了转换处理的规范。
 * 转换处理器负责处理特定的转换逻辑，可以通过实现此接口来扩展换换处理能力。
 *
 * @author: chance
 * @date: 2024/6/20 15:58
 * @since: 1.0
 */
public interface TransHandler {

    /**
     * 判断转换处理器是否以异步方式执行。
     *
     * @return true表示异步执行，false表示同步执行。
     */
    boolean isAsync();

    /**
     * 执行具体业务
     * 接收到转换请求后，会调用此方法来执行具体的转换逻辑。
     *
     * @param context 上下文（包含了转换的相关信息和状态）
     * @return true则继续执行下一个Handler，否则结束Handler Chain的执行直接返回
     */
    boolean handle(TransHandlerContext context);
}
