package com.chance.common.callback;

import com.chance.common.context.TransHandlerContext;

/**
 * 转换回调接口定义
 *
 * @author: chance
 * @date: 2024/6/21 15:01
 * @since: 1.0
 */
public interface TransCallback {

    /**
     * 回调处理方法
     *
     * @param context 上下文
     */
    void onDone(TransHandlerContext context);
}
