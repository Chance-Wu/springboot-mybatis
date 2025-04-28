package com.chance.designpartterms;

import com.chance.designpatterns.activeobject.LogProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author chance
 * @date 2024/12/4 13:20
 * @since 1.0
 */
@Slf4j
public class ActiveObjectTest {

    @Test
    public void log() {
        // 创建日志代理，线程池大小为3
        LogProxy logProxy = new LogProxy(3);
        logProxy.start();

        // 模拟多线程提交日志
        for (int i = 0; i < 10; i++) {
            final int index = i;
            new Thread(() -> logProxy.log("Log message " + index)).start();
        }

        // 停止日志系统
        try {
            // 等待日志写入完成
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logProxy.stop();
    }
}
