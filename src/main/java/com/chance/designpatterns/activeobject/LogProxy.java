package com.chance.designpatterns.activeobject;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 日志代理（Proxy）
 * 该类用于将日志记录请求委托给专门的日志调度器，以异步方式处理日志记录任务
 * 它通过一个阻塞队列来缓存日志记录任务，然后由日志调度器根据需要处理这些任务
 *
 * @author chance
 * @date 2024/12/4 13:06
 * @since 1.0
 */
public class LogProxy {

    /**
     * 任务队列，用于缓存待处理的日志记录任务
     */
    private final BlockingQueue<Runnable> taskQueue;

    /**
     * 日志调度器，负责根据需要处理日志记录任务
     */
    private final LogScheduler logScheduler;

    /**
     * 构造方法，初始化LogProxy实例
     *
     * @param threadPoolSize 线程池大小，决定同时处理日志任务的最大线程数
     */
    public LogProxy(int threadPoolSize) {
        this.taskQueue = new LinkedBlockingQueue<>();
        this.logScheduler = new LogScheduler(taskQueue, threadPoolSize);
    }

    /**
     * 记录日志的方法，将日志记录任务提交到任务队列中
     *
     * @param message 日志信息，需要记录的消息
     */
    public void log(String message) {
        try {
            // 将任务放入队列
            taskQueue.put(new LogTask(message));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 启动日志调度器，开始处理日志记录任务
     */
    public void start() {
        logScheduler.start();
    }

    /**
     * 停止日志调度器，不再处理新的日志记录任务
     */
    public void stop() {
        logScheduler.stop();
    }
}
