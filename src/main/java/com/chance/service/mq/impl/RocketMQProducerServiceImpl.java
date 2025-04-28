//package com.chance.service.mq.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.chance.entity.User;
//import com.chance.service.mq.RocketMQProducerService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.producer.SendCallback;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.client.producer.TransactionSendResult;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.apache.rocketmq.spring.support.RocketMQHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.List;
//
///**
// * 生产者
// *
// * @author: chance
// * @date: 2024/9/6 16:57
// * @since: 1.0
// */
//@Slf4j
//@Service
//public class RocketMQProducerServiceImpl implements RocketMQProducerService {
//
//    @Value("${rocketmq.producer.sendMessageTimeout}")
//    private Integer messageTimeOut;
//
//    /**
//     * 正常规模项目统一用一个 TOPIC
//     */
//    private static final String TOPIC = "CHANCE_TEST_TOPIC";
//
//    /**
//     * 标签1
//     */
//    private static final String TAG_1 = ":TAG_1";
//
//    /**
//     * 标签2
//     */
//    private static final String TAG_2 = ":TAG_2";
//
//    /**
//     * 标签3
//     */
//    private static final String TAG_3 = ":TAG_3";
//
//    /**
//     * 标签4
//     */
//    private static final String TAG_4 = ":TAG_4";
//
//    /**
//     * 标签5
//     */
//    private static final String TAG_5 = ":TAG_5";
//
//    /**
//     * 直接注入使用，用于发送消息到 broker 服务器
//     */
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//    @Override
//    public void send(User user) {
//        rocketMQTemplate.convertAndSend(TOPIC + TAG_1, user);
////      rocketMQTemplate.send(TOPIC + ":TAG_1", MessageBuilder.withPayload(user).build()); // 等价于上面一行
//    }
//
//    @Override
//    public SendResult sendMsg(String msgBody) {
//        // syncSend() 方法会阻塞当前线程。
//        // 成功返回 SendResult 对象，包含了消息的发送状态、消息ID等信息；失败 syncSend 会抛出 MessagingException 异常。
//        SendResult sendResult = rocketMQTemplate.syncSend(TOPIC + TAG_1, MessageBuilder.withPayload(msgBody).build());
//        log.info("【sendMsg】sendResult={}", JSON.toJSONString(sendResult));
//        return sendResult;
//    }
//
//    @Override
//    public void sendAsyncMsg(String msgBody) {
//        // 不会阻塞当前线程，asyncSend方法会立即返回。
//        // 如果需要等待消息发送完成并处理发送结果，可以使用SendCallback回调接口。
//        rocketMQTemplate.asyncSend(TOPIC + TAG_1, MessageBuilder.withPayload(msgBody).build(), new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                // 处理消息发送成功逻辑
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                // 处理消息发送异常逻辑
//            }
//        });
//    }
//
//    @Override
//    public void sendOneWayMsg(String msgBody) {
//        rocketMQTemplate.sendOneWay(TOPIC + TAG_1, MessageBuilder.withPayload(msgBody).build());
//    }
//
//    @Override
//    public void sendOneWayOrderlyMsg(String msgBody, String id) {
//        rocketMQTemplate.sendOneWayOrderly(TOPIC + TAG_2, MessageBuilder.withPayload(msgBody).build(), id);
//    }
//
//    @Override
//    public void sendSyncOneWayOrderlyMsg(String msgBody, String id) {
//        SendResult sendResult = rocketMQTemplate.syncSendOrderly(TOPIC + TAG_2, MessageBuilder.withPayload(msgBody).build(), id);
//        log.info("【sendSyncOneWayOrderlyMsg】sendResult={}", JSON.toJSONString(sendResult));
//    }
//
//    @Override
//    public void sendAsyncOneWayOrderlyMsg(String msgBody, String id) {
//        rocketMQTemplate.asyncSendOrderly(TOPIC + TAG_2, MessageBuilder.withPayload(msgBody).build(), id, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                log.info(sendResult.toString());
//            }
//
//            @Override
//            public void onException(Throwable e) {
//                log.info(e.getMessage());
//            }
//        });
//    }
//
//    @Override
//    public void syncSendDelayTimeSecondsMsg(String msgBody, int delayLevel) {
//        // 秒级
//        SendResult sendResult = rocketMQTemplate.syncSendDelayTimeSeconds(TOPIC + TAG_3, msgBody, delayLevel);
//        // 毫秒级
//        // SendResult sendResult =rocketMQTemplate.syncSendDelayTimeMills(TOPIC+TAG_2,msgBody, delayLevel);
//        log.info("【syncSendDelayTimeSecondsMsg】sendResult={}", JSON.toJSONString(sendResult));
//    }
//
//    @Override
//    public void syncSendDeliverTimeMillsMsg(String msgBody) {
//        // 每天凌晨处理
//        long time = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//        SendResult sendResult = rocketMQTemplate.syncSendDeliverTimeMills(TOPIC + TAG_3, msgBody, time);
//        log.info("【syncSendDelayTimeSecondsMsg】sendResult={}", JSON.toJSONString(sendResult));
//    }
//
//    @Override
//    public void syncSendBatchMessage(List<String> msgs) {
//        SendResult sendResult = rocketMQTemplate.syncSend(TOPIC + TAG_4, msgs);
//        log.info("【syncSendBatchMessage】sendResult={}", JSON.toJSONString(sendResult));
//    }
//
//    @Override
//    public void sendMessageInTransactionMsg(String msgBody) {
//        Message<String> msgs = MessageBuilder.withPayload(JSON.toJSONString(msgBody))
//                .setHeader("KEYS", msgBody)
//                //设置事务ID
//                .setHeader(RocketMQHeaders.TRANSACTION_ID, "KEY_" + msgBody)
//                .build();
//        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(TOPIC + TAG_5, msgs, null);
//        log.info("【sendMessageInTransactionMsg】transactionSendResult={}", JSON.toJSONString(transactionSendResult));
//    }
//
//}
