package com.chance.designpatterns.factory.simple;

/**
 * 产品接口
 *
 * @author chance
 * @date 2025/12/6 09:50
 * @since 1.0
 */
public interface NotificationSender {

    void send(String recipient, String message);
}
