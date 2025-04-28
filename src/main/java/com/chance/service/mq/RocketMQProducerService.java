//package com.chance.service.mq;
//
//import com.chance.entity.User;
//import org.apache.rocketmq.client.producer.SendResult;
//
//import java.util.List;
//
///**
// * @author: chance
// * @date: 2024/9/9 13:35
// * @since: 1.0
// */
//public interface RocketMQProducerService {
//
//    /**
//     * 普通消息发送， 测试
//     *
//     * @param user 用户信息
//     */
//    void send(User user);
//
//    /**
//     * 同步方法
//     *
//     * @param msgBody 消息体
//     * @return 发送结果
//     */
//    SendResult sendMsg(String msgBody);
//
//    /**
//     * 异步方法
//     *
//     * @param msgBody 消息体
//     */
//    void sendAsyncMsg(String msgBody);
//
//    /**
//     * 单向消息（只负责发送消息，不等应答，不关心结果）
//     *
//     * @param msgBody 消息体
//     */
//    void sendOneWayMsg(String msgBody);
//
//    /**
//     * 单向顺序消息
//     *
//     * @param msgBody 消息体
//     * @param id      消息id
//     */
//    void sendOneWayOrderlyMsg(String msgBody, String id);
//
//    /**
//     * 同步顺序消息
//     *
//     * @param msgBody 消息体
//     * @param id      消息id
//     */
//    void sendSyncOneWayOrderlyMsg(String msgBody, String id);
//
//    /**
//     * 异步顺序消息
//     *
//     * @param msgBody 消息体
//     * @param id      消息id
//     */
//    void sendAsyncOneWayOrderlyMsg(String msgBody, String id);
//
//    /**
//     * 延时消息
//     *
//     * @param msgBody    消息体
//     * @param delayLevel 延时时间
//     */
//    void syncSendDelayTimeSecondsMsg(String msgBody, int delayLevel);
//
//    /**
//     * 定时消息
//     *
//     * @param msgBody 消息体
//     */
//    void syncSendDeliverTimeMillsMsg(String msgBody);
//
//    /**
//     * 批量发送
//     *
//     * @param msgs 消息列表
//     */
//    void syncSendBatchMessage(List<String> msgs);
//
//
//    /**
//     * 事务消息
//     *
//     * @param msgBody 消息体
//     */
//    void sendMessageInTransactionMsg(String msgBody);
//}
