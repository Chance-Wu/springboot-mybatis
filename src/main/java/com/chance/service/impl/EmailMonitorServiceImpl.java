package com.chance.service.impl;

import com.chance.service.EmailMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @Description: EmailMonitorServiceImpl
 * @Author: Administrator
 * @Date: 2025-4-26 9:08
 * @Version 1.0
 */
@Slf4j
@Service
public class EmailMonitorServiceImpl implements EmailMonitorService {

    public static final String INBOX = "INBOX";

    @Override
    public void checkEmailsForAllAccounts() {
        // TODO 从账户管理里取数据
        String user = "wuchenyang@heilangroup.cn";
        String password = "Ww539976";
        String pushAccount = "60004727";
        List<String> pushAccounts = new ArrayList<>();
        pushAccounts.add(pushAccount);
        String protocol = "imap";
        String host = "172.26.0.60";
        int port = 143;
        // 获取邮箱会话
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", protocol);
        Session session = Session.getInstance(props, null);
        Store store = null;
        Folder inbox = null;
        try {
            // 连接到邮箱
            store = session.getStore(protocol);
            log.info(">>>>>>>> start connect to mail");
            store.connect(host, port, user, password);

            // 打开收件箱
            inbox = store.getFolder(INBOX);
            inbox.open(Folder.READ_WRITE);

            // 搜索未读邮件
            List<Message> messages = Arrays.asList(inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false)));
            log.info(">>>>>>>> 未读邮件数量：{}", messages.size());
            if (!messages.isEmpty()) {
                List<String> subjects = new ArrayList<>();
                for (Message message : messages) {
                    String subject = message.getSubject();
                    subjects.add(subject);
                }
                // 触发推送消息事件
                triggerPushMsgEvent(pushAccounts, subjects);
            }
        } catch (Exception e) {
            log.error(">>>>>>>> 处理失败：", e);
        } finally {
            // 确保资源被正确关闭
            closeResources(inbox, store);
        }
    }

    /**
     * 关闭邮箱相关资源
     */
    private void closeResources(Folder inbox, Store store) {
        try {
            if (inbox != null && inbox.isOpen()) {
                inbox.close(true);
            }
        } catch (MessagingException e) {
            log.warn(">>>>>>>> 关闭收件箱失败：", e);
        }

        try {
            if (store != null && store.isConnected()) {
                store.close();
            }
        } catch (MessagingException e) {
            log.warn(">>>>>>>> 关闭邮箱存储失败：", e);
        }
    }

    private void triggerPushMsgEvent(List<String> accounts, List<String> subjects) {
        // 在这里实现事件触发逻辑
    }

}