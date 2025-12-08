package com.chance.designpatterns.factory.simple;

/**
 * 具体产品 B
 *
 * @author chance
 * @date 2025/12/6 09:53
 * @since 1.0
 */
public class SmsSender implements NotificationSender {

    @Override
    public void send(String recipient, String message) {
        System.out.println("发送短信给 " + recipient + ": " + message);
    }
}
