package com.chance.designpatterns.activeobject;

/**
 * 日志记录任务（命令对象）
 * 该类实现了{@link Runnable}接口，用于执行日志记录任务
 * 它封装了日志消息，并在run方法中定义了如何记录日志
 *
 * @author chance
 * @date 2024/12/4 13:04
 * @since 1.0
 */
public class LogTask implements Runnable {

    /**
     * 日志消息
     */
    private final String message;

    /**
     * 构造方法，创建一个LogTask实例
     *
     * @param message 要记录的日志消息
     */
    public LogTask(String message) {
        this.message = message;
    }

    /**
     * 实现Runnable接口的run方法
     * 该方法定义了日志记录的操作
     * 在这个例子中，它模拟了日志写入操作，实际应用中可以替换为写入日志文件或数据库等操作
     */
    @Override
    public void run() {
        // 模拟日志写入（可以是写文件、写数据库等）
        System.out.println(Thread.currentThread().getName() + ": " + message);
    }
}
