package com.chance.common.handler;

import com.chance.common.ResultCode;
import com.chance.common.context.TransHandlerContext;
import com.chance.common.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认转换流水线，负责处理转换流程的管理。
 * 通过链式处理方式，管理转换处理器的执行顺序。
 *
 * @author: chance
 * @date: 2024/6/21 15:35
 * @since: 1.0
 */
public class DefaultTransPipeline implements TransPipeline {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransPipeline.class);

    /**
     * 头节点，用于标记链式处理的开始
     */
    private final TransHandlerNode head = new TransHandlerNode();

    /**
     * 尾节点，用于标记链式处理的结束
     */
    private TransHandlerNode tail;

    /**
     * 当前处理上下文
     */
    private TransHandlerContext context = null;

    /**
     * 构造函数，初始化流水线的上下文
     *
     * @param context 流水线处理上下文
     */
    public DefaultTransPipeline(TransHandlerContext context) {
        this.context = context;
    }

    /**
     * 在链式处理的头部添加转换处理器。
     *
     * @param handlers 要添加的转换处理器数组
     */
    @Override
    public void addFirst(TransHandler... handlers) {
        TransHandlerNode pre = head.getNext();
        for (TransHandler handler : handlers) {
            if (null == handler) {
                continue;
            }
            TransHandlerNode node = new TransHandlerNode();
            node.setHandler(handler);
            node.setNext(pre);
            pre = node;
        }

        head.setNext(pre);
    }

    /**
     * 在链式处理的尾部添加转换处理器。
     *
     * @param handlers 要添加的转换处理器数组
     */
    @Override
    public void addLast(TransHandler... handlers) {
        TransHandlerNode next = tail;
        for (TransHandler handler : handlers) {
            if (null == handler) {
                continue;
            }
            TransHandlerNode node = new TransHandlerNode();
            node.setHandler(handler);
            node.setNext(node);
            next = node;
        }

        tail = next;
    }

    /**
     * 启动流水线，从链头开始执行所有的转换处理器。
     */
    @Override
    public void start() {
        try {
            head.getNext().execute(getContext());
        } catch (Exception e) {
            LOGGER.error(">>>>>>>> pipeline系统运行异常 <<<<<<<<", e);
            throw new BizException(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMessage().concat(":pipeline运行异常"));
        }
    }

    /**
     * 关闭流水线，执行清理或资源释放操作。
     */
    @Override
    public void shutDown() {

    }

    /**
     * 获取当前处理上下文。
     *
     * @return 当前处理上下文对象
     */
    @Override
    public <T extends TransHandlerContext> T getContext() {
        return (T) context;
    }

    /**
     * 设置当前处理上下文。
     *
     * @param context 当前处理上下文对象
     */
    public void setContext(TransHandlerContext context) {
        this.context = context;
    }
}
