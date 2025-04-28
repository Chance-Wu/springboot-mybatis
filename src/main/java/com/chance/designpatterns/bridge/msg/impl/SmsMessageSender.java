package com.chance.designpatterns.bridge.msg.impl;

import com.chance.designpatterns.bridge.msg.MessageSender;

/**
 * @author chance
 * @date 2024/12/30 13:04
 * @since 1.0
 */
public class SmsMessageSender implements MessageSender {
    @Override
    public void send(String message) {
        System.out.println("通过短信发送消息：" + message);
    }
}
