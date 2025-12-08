package com.chance.designpatterns.factory.method;

import com.chance.designpatterns.factory.simple.EmailSender;
import com.chance.designpatterns.factory.simple.NotificationSender;

/**
 * 具体工厂 A：只创建 EmailSender
 *
 * @author chance
 * @date 2025/12/6 10:45
 * @since 1.0
 */
public class EmailFactory implements NotificationFactory {

    @Override
    public NotificationSender createSender() {
        return new EmailSender();
    }
}
