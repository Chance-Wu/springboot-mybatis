package com.chance.designpatterns.factory.simple;

/**
 * 简单工厂类
 *
 * @author chance
 * @date 2025/12/6 10:02
 * @since 1.0
 */
public class SenderFactory {

    /**
     * 简单工厂通常使用静态方法
     */
    public static NotificationSender createSender(String type) {
        if ("EMAIL".equalsIgnoreCase(type)) {
            return new EmailSender();
        } else if ("SMS".equalsIgnoreCase(type)) {
            return new SmsSender();
        } else {
            throw new IllegalArgumentException("不支持的发送类型: " + type);
        }
    }
}
