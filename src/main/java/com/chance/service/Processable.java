package com.chance.service;

import java.time.LocalDateTime;

/**
 * @author chance
 * @date 2025/12/5 16:20
 * @since 1.0
 */
public interface Processable {

    /**
     * 抽象方法：强制实现业务核心逻辑
     */
    void process(Object data);

    /**
     * 默认方法：注入通用或辅助逻辑
     */
    default boolean preValidate() {
        System.out.println("Processing：执行默认前置校验...");
        // 默认返回 true，除非实现类选择重写
        return true;
    }

    // 默认方法可以调用其他抽象方法或默认方法
    default void logStart(String handlerName) {
        System.out.println(handlerName + " Start at: " + LocalDateTime.now());
    }
}
