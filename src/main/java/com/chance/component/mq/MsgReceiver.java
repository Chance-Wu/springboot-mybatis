package com.chance.component.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: MsgReceiver
 * @author: chance
 * @date: 2022/10/10 00:34
 * @since: 1.0
 */
@Component
@RabbitListener(queues = "QUEUE_A")
public class MsgReceiver {

    private final Logger logger = LoggerFactory.getLogger(MsgReceiver.class);

    @RabbitHandler
    public void process(String content) {
        logger.info(">>>>>>>>接收处理队列A当中的消息：{}", content);
    }
}
