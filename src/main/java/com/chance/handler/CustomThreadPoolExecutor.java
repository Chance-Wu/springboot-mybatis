package com.chance.handler;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: chance
 * @date: 2024/11/12 14:53
 * @since: 1.0
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                    LinkedBlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                // 如果任务是Future类型，尝试获取结果可能会抛出异常
                Future<?> future = (Future<?>) r;
                if (future.isDone()) {
                    future.get(); // 这里可能会抛出异常
                }
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // 忽略中断，恢复中断状态
            }
        }
        if (t != null) {
            System.err.println("Task threw an exception: " + t);
            t.printStackTrace();
            // 可以在这里添加更多的错误处理逻辑，比如记录日志、发送通知等
        }
    }
}
