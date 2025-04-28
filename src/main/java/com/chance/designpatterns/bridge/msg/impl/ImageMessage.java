package com.chance.designpatterns.bridge.msg.impl;

import com.chance.designpatterns.bridge.msg.Message;
import com.chance.designpatterns.bridge.msg.MessageSender;

/**
 * 图片消息
 *
 * @author chance
 * @date 2024/12/30 14:08
 * @since 1.0
 */
public class ImageMessage extends Message {

    /**
     * 图像的文件路径
     */
    private final String imagePath;

    /**
     * 构造函数，初始化ImageMessage对象
     *
     * @param messageSender MessageSender对象，用于发送消息
     * @param imagePath     图像的文件路径
     */
    public ImageMessage(MessageSender messageSender, String imagePath) {
        super(messageSender);
        this.imagePath = imagePath;
    }

    /**
     * 发送图像消息的方法
     * 调用MessageSender的send方法来发送图像路径
     */
    @Override

    public void send() {
        messageSender.send("图片路径：" + imagePath);
    }
}
