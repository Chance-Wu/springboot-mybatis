package com.chance.designpatterns.asyncmethodinvocation;

import java.util.concurrent.CompletableFuture;

/**
 * @author chance
 * @date 2024/12/11 16:33
 * @since 1.0
 */
public class CallbackExample {

    public static void main(String[] args) {
        performTask(result -> System.out.println("Task completed with result: " + result));
        System.out.println("Main thread continues...");
    }

    public static void performTask(Callback callback) {
        CompletableFuture.runAsync(() -> {
            try {
                // 模拟耗时操作
                Thread.sleep(1000);
                callback.onComplete("Success");
            } catch (InterruptedException e) {
                callback.onComplete("Failure");
            }
        });
    }

    interface Callback {
        void onComplete(String result);
    }
}
