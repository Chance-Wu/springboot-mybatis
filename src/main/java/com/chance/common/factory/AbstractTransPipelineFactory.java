package com.chance.common.factory;

import com.chance.common.AbstractRequest;
import com.chance.common.context.AbstractTransHandlerContext;
import com.chance.common.context.TransHandlerContext;
import com.chance.common.convert.TransConvert;
import com.chance.common.handler.DefaultTransPipeline;
import com.chance.common.handler.TransPipeline;
import com.chance.common.invoker.TransProcessPipelineInvoker;

/**
 * @author: chance
 * @date: 2024/6/21 16:37
 * @since: 1.0
 */
public abstract class AbstractTransPipelineFactory<T extends AbstractRequest> implements TransPipelineFactory<T> {

    /**
     * 创建流水线
     *
     * @param obj 请求实体
     * @return 转换流水调用器
     */
    @Override
    public TransProcessPipelineInvoker build(T obj) {
        // 创建转换器
        TransConvert convert = createConvert();
        // 创建上下文
        TransHandlerContext context = createContext();
        // 上朔
        AbstractTransHandlerContext absCtx = (AbstractTransHandlerContext) context;

        // 设置转换器
        absCtx.setConvert(convert);
        // 上下文转换
        convert.requestContext(obj, context);
        // 创建管道
        TransPipeline pipeline = createPipeline(context);
        // build管道
        doBuild(pipeline);
        // 返回
        return pipeline;
    }

    /**
     * 构建管道处理节点
     *
     * @param pipeline 管道
     */
    protected abstract void doBuild(TransPipeline pipeline);

    /**
     * 创建管道处理实例
     *
     * @param context 上下文
     * @return 管道实例
     */
    private TransPipeline createPipeline(TransHandlerContext context) {
        return new DefaultTransPipeline(context);
    }

    /**
     * 创建上下文
     *
     * @return 上下文
     */
    protected abstract TransHandlerContext createContext();

    /**
     * 创建转换器
     * 子类去实现自定义转换类
     *
     * @return
     */
    protected abstract TransConvert createConvert();
}
