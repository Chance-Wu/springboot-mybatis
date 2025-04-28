package com.chance.designpatterns.activeobject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 日志调度器（Scheduler）
 * 负责管理日志处理任务的调度与执行
 *
 * @author chance
 * @date 2024/12/4 13:10
 * @since 1.0
 */
public class LogScheduler {

    /**
     * 任务队列，用于存放待执行的日志处理任务
     */
    private final BlockingQueue<Runnable> taskQueue;

    /**
     * 线程池，用于执行日志处理任务
     */
    private final ExecutorService threadPool;

    /**
     * 运行状态标志，用于控制日志调度器的启动与停止
     */
    private volatile boolean running = true;

    /**
     * 构造方法，初始化日志调度器
     *
     * @param taskQueue      任务队列，用于存放待执行的任务
     * @param threadPoolSize 线程池大小，决定同时执行任务的数量
     */
    public LogScheduler(BlockingQueue<Runnable> taskQueue, int threadPoolSize) {
        this.taskQueue = taskQueue;
        // 创建固定大小的线程池
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
    }

    /**
     * 启动日志调度器
     * 在一个新线程中循环检查任务队列，并提交任务到线程池执行
     */
    public void start() {
        new Thread(() -> {
            while (running) {
                try {
                    // 从队列中取出任务
                    Runnable task = taskQueue.take();
                    // 提交到线程池执行
                    threadPool.submit(task);
                } catch (InterruptedException e) {
                    // 捕获中断异常，恢复中断状态
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    /**
     * 停止日志调度器
     * 设置running状态为false，并关闭线程池
     */
    public void stop() {
        running = false;
        threadPool.shutdown();
    }
}
