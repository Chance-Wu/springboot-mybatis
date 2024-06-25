package com.chance.common.convert;

import com.chance.common.AbstractRequest;
import com.chance.common.CommonRsp;
import com.chance.common.context.TransHandlerContext;
import org.apache.poi.ss.formula.functions.T;

/**
 * 上下文转换接口
 *
 * @author: chance
 * @date: 2024/6/21 13:48
 * @since: 1.0
 */
public interface TransConvert {

    /**
     * 请求上下文转换
     *
     * @param req     请求
     * @param context 请求上下文
     * @return 转换上下文
     */
    TransHandlerContext requestContext(AbstractRequest req, TransHandlerContext context);

    /**
     * 上下文转响应
     *
     * @param context 请求上下文
     * @return 相应上下文
     */
    CommonRsp<T> convertContextToRsp(TransHandlerContext context);
}
