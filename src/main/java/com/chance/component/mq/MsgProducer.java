package com.chance.component.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @description: MsgProducer
 * @author: chance
 * @date: 2022/10/9 23:15
 * @since: 1.0
 */
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    private final Logger logger = LoggerFactory.getLogger(MsgProducer.class);

    /**
     * 由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
     */
    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入rabbitTemplate
     */
    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // rabbitTemplate如果为单例的话，那回调就是最后设置的内容
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(String exchange, String routingKey, String content) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 把消息放入routingKey对应的队列当中去
        rabbitTemplate.convertAndSend(exchange, routingKey, content, correlationData);
    }

    /**
     * 消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(">>>>>>>>回调id：{}", correlationData);
        if (ack) {
            logger.info(">>>>>>>>message send successful");
        } else {
            logger.info(">>>>>>>>message send failure:{}", cause);
        }
    }
}
