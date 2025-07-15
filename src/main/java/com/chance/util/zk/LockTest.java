package com.chance.util.zk;

import java.util.concurrent.TimeUnit;

/**
 * @author chance
 * @date 2025/7/10 15:19
 * @since 1.0
 */
public class LockTest {

    public static void main(String[] args) {
        String lockPath = "/locks/test_lock";

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " 正在尝试获取锁...");
                boolean isLocked = ZkDistributedLock.lock(lockPath, 10, TimeUnit.SECONDS);

                if (isLocked) {
                    System.out.println(Thread.currentThread().getName() + " 成功获取锁");
                    // 模拟业务处理
                    Thread.sleep(3000);
                    ZkDistributedLock.unlock(lockPath);
                    System.out.println(Thread.currentThread().getName() + " 释放锁");
                } else {
                    System.out.println(Thread.currentThread().getName() + " 获取锁失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // 多线程模拟并发获取锁
        for (int i = 0; i < 5; i++) {
            new Thread(task, "Thread-" + i).start();
        }

        // 应用关闭前关闭 zk 客户端
        Runtime.getRuntime().addShutdownHook(new Thread(ZkDistributedLock::close));
    }
}
