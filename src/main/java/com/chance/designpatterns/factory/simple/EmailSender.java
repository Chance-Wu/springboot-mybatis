package com.chance.designpatterns.factory.simple;

/**
 * 具体产品 A
 *
 * @author chance
 * @date 2025/12/6 09:52
 * @since 1.0
 */
public class EmailSender implements NotificationSender {

    @Override
    public void send(String recipient, String message) {
        System.out.println("发送邮件给 " + recipient + ": " + message);
    }
}
