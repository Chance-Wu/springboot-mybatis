package com.chance.designpatterns.asyncmethodinvocation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author chance
 * @date 2024/12/11 13:57
 * @since 1.0
 */
public class FutureExample {

    public static void main(String[] args) {
        // 创建一个单线程的 ExecutorService
        ExecutorService executor = Executors.newSingleThreadExecutor();

        // 提交一个异步任务，并获取 Future 对象
        Future<String> future = executor.submit(() -> {
            // 模拟耗时操作
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 重置中断状态
                throw new RuntimeException("Thread was interrupted", e);
            }
            return "success";
        });

        // 做其他事情
        System.out.println("Doing something else...");

        // 阻塞等待结果
        try {
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for result: " + e.getMessage());
            // 重置中断状态
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.err.println("Error occurred during task execution: " + e.getCause().getMessage());
        } finally {
            executor.shutdown();
        }
    }
}
