package com.chance.common.handler;

import com.chance.common.callback.TransCallback;
import com.chance.common.context.TransHandlerContext;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 责任链中的单个节点，持有对下一个节点的引用以及一个处理逻辑
 *
 * @author: chance
 * @date: 2024/6/20 15:55
 * @since: 1.0
 */
@Data
public class TransHandlerNode {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TransHandlerNode.class);

    /**
     * 处理逻辑
     */
    private TransHandler handler;

    /**
     * 下一个节点
     */
    private TransHandlerNode next = null;

    /**
     * 执行流水线任务
     *
     * @param context 上下文
     */
    public void execute(TransHandlerContext context) {
        LOGGER.info(">>>>>>>> process pipeline start <<<<<<<<");
        AbstractTransHandler transHandler = (AbstractTransHandler) handler;
        boolean success = handler.handle(context);
        // 回调函数
        execCallback(transHandler.getTransCallback(), context, null);
        if (next != null) {
            if (success) {
                if (transHandler.isAsync()) {
                    // TODO 如果为true，则采用异步线程去执行任务
                }
                // 继续执行下一个流水线
                next.execute(context);
            }
        }
    }

    /**
     * 异常处理回调方法
     *
     * @param callback 回调方法
     * @param context  上下文
     * @param ex       异常
     */
    private void execCallback(TransCallback callback, TransHandlerContext context, Throwable ex) {
        try {
            if (ex == null && callback != null) {
                callback.onDone(context);
            }
        } catch (Exception e) {
            LOGGER.error(">>>>>>>> Pipeline回调处理异常 <<<<<<<<", e);
        }
    }

}
