package com.chance.designpatterns.factory.method;

import com.chance.designpatterns.factory.simple.NotificationSender;
import com.chance.designpatterns.factory.simple.SmsSender;

/**
 * 具体工厂 B：只创建 SmsSender
 *
 * @author chance
 * @date 2025/12/6 10:48
 * @since 1.0
 */
public class SmsFactory implements NotificationFactory {

    @Override
    public NotificationSender createSender() {
        return new SmsSender();
    }
}
