package com.chance.designpatterns.bridge.msg;

/**
 * 定义发送渠道接口
 *
 * @author chance
 * @date 2024/12/30 10:57
 * @since 1.0
 */
public interface MessageSender {

    /**
     * 发送消息的方法
     *
     * @param message 要发送的消息内容，不能为空
     *                实现此接口的类需要能够处理将此消息发送到指定的目的地
     *                消息的格式和内容由实现者和调用者共同约定
     */
    void send(String message);
}
