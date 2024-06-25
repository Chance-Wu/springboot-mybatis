package com.chance.common.handler;

import com.chance.common.callback.TransCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象转换处理类，作为转换处理的基类，提供通用的处理逻辑和机制。
 * 该类为抽象类，不允许直接实例化，旨在被具体的转换处理类继承。
 *
 * @author chance
 * @since 1.0
 */
public abstract class AbstractTransHandler implements TransHandler {

    /**
     * 日志记录器，用于记录类的运行时信息。
     */
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    /**
     * 默认常量，用于指示默认的处理策略或配置。
     */
    public static final String DEFAULT = "default";

    /**
     * 获取转换回调对象。
     * 该方法提供了一个钩子，允许子类实现特定的转换回调逻辑。
     * 如果不需要特定的回调逻辑，子类可以重写此方法返回null。
     *
     * @return 可能的转换回调对象，如果没有特定的回调逻辑，则返回null。
     */
    public TransCallback getTransCallback() {
        return null;
    }
}

