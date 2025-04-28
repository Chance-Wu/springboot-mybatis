package com.chance.designpatterns.asyncmethodinvocation;

import java.util.concurrent.CompletableFuture;

/**
 * @author chance
 * @date 2024/12/11 15:04
 * @since 1.0
 */
public class CompletableFutureExample {

    public static void main(String[] args) {
        // 创建一个异步任务
        CompletableFuture.supplyAsync(() -> {
                    try {
                        // 模拟耗时操作
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "Task completed";
                })
                // 当任务完成后，处理任务的结果
                .thenAccept(result -> System.out.println("Result: " + result));

        // 主进程继续执行
        System.out.println("Main thread continues...");
    }
}
