package com.chance.thread;

/**
 * @author chance
 * @date 2025/12/6 16:47
 * @since 1.0
 */
public class StatusVo {

    // 假设这是两个线程共享的变量
    private volatile boolean isRunning = true;

    // 线程 A 在另一个线程 B 未知的情况下将其设置为 false
    public void stop() {
        isRunning = false;
    }

    // 线程 B 的循环可能永远不会停止，因为它可能只读取 CPU 缓存中的旧值 true
    public void run() {
        while (isRunning) {
            // ... 业务代码 ...
        }
    }
}
