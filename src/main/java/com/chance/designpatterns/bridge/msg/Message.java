package com.chance.designpatterns.bridge.msg;

/**
 * 定义消息抽象类
 *
 * @author chance
 * @date 2024/12/30 10:55
 * @since 1.0
 */
public abstract class Message {

    /**
     * MessageSender是实际发送消息的工具，由外部传入并初始化
     */
    protected MessageSender messageSender;

    /**
     * 构造方法，初始化Message实例
     *
     * @param messageSender 消息发送器，负责执行实际的消息发送操作
     */
    protected Message(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * 抽象方法，定义了发送消息的操作
     * 子类必须实现此方法，以提供具体的消息发送逻辑
     */
    public abstract void send();
}
