package com.chance.util;

import com.chance.handler.CustomThreadPoolExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author: chance
 * @date: 2024/11/12 13:46
 * @since: 1.0
 */
public class ExecutorTest {

    public static void main(String[] args) {
        /*ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            Future<?> future = threadPool.submit(() -> {
                System.out.println("current thread name" + Thread.currentThread().getName());
                Object object = null;
                System.out.println("result## " + object.toString());
            });
            try {
                future.get();
            } catch (Exception e) {
                System.out.println("发生异常");
            }
        }
        // 关闭线程池
        executor.shutdown();*/

        /*ExecutorService threadPool = Executors.newFixedThreadPool(1, r -> {
            Thread t = new Thread(r);
            t.setUncaughtExceptionHandler((t1, e) -> System.out.println(t1.getName() + "线程抛出的异常" + e));
            return t;
        });

        threadPool.execute(() -> {
            Object object = null;
            System.out.println("result## " + object.toString());
        });
        threadPool.shutdown();*/

        CustomThreadPoolExecutor executor = new CustomThreadPoolExecutor(2, 4, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        executor.execute(() -> {
            Object object = null;
            System.out.println("result## " + object.toString());
        });
        // 关闭线程池
        executor.shutdown();
    }
}
