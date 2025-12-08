package com.chance.designpatterns.factory.method;

import com.chance.designpatterns.factory.simple.NotificationSender;

/**
 * 抽象工厂
 *
 * @author chance
 * @date 2025/12/6 10:39
 * @since 1.0
 */
public interface NotificationFactory {

    /**
     * 抽象方法：由子工厂实现
     */
    NotificationSender createSender();
}
