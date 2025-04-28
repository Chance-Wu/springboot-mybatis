//package com.chance.service.mq.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.chance.entity.User;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
///**
// * 消费者
// *
// * @author: chance
// * @date: 2024/9/9 09:12
// * @since: 1.0
// */
//
//@Slf4j
//@Component
//public class MQConsumerService {
//
//    /**
//     * topic要和生产者topic一致；consumerGroup 必须指定；selectorExpression 是tag，默认为“*”，不设置会监听所有消息。
//     */
//    @Service
//    @RocketMQMessageListener(topic = "CHANCE_TEST_TOPIC", selectorExpression = "TAG_1", consumerGroup = "CON_GROUP_ONE")
//    public static class ConsumerSend implements RocketMQListener<User> {
//        // 监听到消息就会执行此方法
//        @Override
//        public void onMessage(User user) {
//            log.info("监听到消息：user={}", JSON.toJSONString(user));
//        }
//    }
//
//    /**
//     * MessageExt：是一个消息接收通配符，不管发送的是String还是对象，都可接收。
//     */
//    @Service
//    @RocketMQMessageListener(topic = "CHANCE_TEST_TOPIC", selectorExpression = "TAG_2", consumerGroup = "CON_GROUP_TWO")
//    public static class Consumer implements RocketMQListener<MessageExt> {
//        @Override
//        public void onMessage(MessageExt messageExt) {
//            byte[] body = messageExt.getBody();
//            String msg = new String(body);
//            log.info("监听到消息：msg={}", msg);
//        }
//    }
//}