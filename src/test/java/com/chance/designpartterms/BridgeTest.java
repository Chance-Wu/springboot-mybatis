package com.chance.designpartterms;

import com.chance.designpatterns.bridge.Alipay;
import com.chance.designpatterns.bridge.FacePayMode;
import com.chance.designpatterns.bridge.PasswordPayMode;
import com.chance.designpatterns.bridge.Payment;
import com.chance.designpatterns.bridge.PaymentMode;
import com.chance.designpatterns.bridge.msg.Message;
import com.chance.designpatterns.bridge.msg.MessageSender;
import com.chance.designpatterns.bridge.msg.impl.EmailMessageSender;
import com.chance.designpatterns.bridge.msg.impl.ImageMessage;
import com.chance.designpatterns.bridge.msg.impl.SmsMessageSender;
import com.chance.designpatterns.bridge.msg.impl.TextMessage;
import com.chance.designpatterns.bridge.msg.impl.WechatMessageSender;
import org.junit.Test;

/**
 * @author chance
 * @date 2024/12/26 15:24
 * @since 1.0
 */
public class BridgeTest {

    @Test
    public void testSendMsg() {
        // 使用短信发送文本消息
        SmsMessageSender smsMessageSender = new SmsMessageSender();
        TextMessage textMessage = new TextMessage(smsMessageSender, "这是一条短信文本消息");
        textMessage.send();

        // 使用邮件发送图片消息
        MessageSender emailSender = new EmailMessageSender();
        Message imageMessageByEmail = new ImageMessage(emailSender, "/path/to/image.jpg");
        imageMessageByEmail.send();

        // 使用微信发送文本消息
        MessageSender wechatSender = new WechatMessageSender();
        Message textMessageByWechat = new TextMessage(wechatSender, "这是一条微信文本消息");
        textMessageByWechat.send();
    }

    @Test
    public void test() {
        PaymentMode facePayMode = new FacePayMode();
        Payment alipayFacePay = new Alipay(facePayMode);
        alipayFacePay.doPay();

        PaymentMode passwordPayMode = new PasswordPayMode();
        Payment alipayPasswordPay = new Alipay(passwordPayMode);
        alipayPasswordPay.doPay();
    }
}
