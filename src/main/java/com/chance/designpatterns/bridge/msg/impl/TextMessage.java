package com.chance.designpatterns.bridge.msg.impl;

import com.chance.designpatterns.bridge.msg.Message;
import com.chance.designpatterns.bridge.msg.MessageSender;

/**
 * 文本消息
 *
 * @author chance
 * @date 2024/12/30 14:06
 * @since 1.0
 */
public class TextMessage extends Message {

    /**
     * 文本消息的内容
     */
    private final String content;

    /**
     * 构造一个 TextMessage 实例
     *
     * @param messageSender 用于发送消息的消息发送接口
     * @param content       文本消息的内容
     */
    public TextMessage(MessageSender messageSender, String content) {
        // 调用父类 Message 的构造方法，初始化消息发送接口
        super(messageSender);
        // 设置文本消息的内容
        this.content = content;
    }

    /**
     * 实现父类 Message 的 send 方法
     * 用于发送文本消息的内容
     */
    @Override
    public void send() {
        // 调用消息发送接口的 send 方法发送文本消息的内容
        messageSender.send(content);
    }
}
