package com.chance.common.context;

import com.chance.common.convert.TransConvert;
import lombok.Data;

/**
 * 默认抽象处理上下文
 *
 * @author: chance
 * @date: 2024/6/21 13:45
 * @since: 1.0
 */
@Data
public abstract class AbstractTransHandlerContext implements TransHandlerContext {

    /**
     * 交易ID
     */
    private String transId;

    /**
     * 转换器
     */
    private TransConvert convert;

}
