package com.chance.thread;

/**
 * @author chance
 * @date 2025/12/6 16:10
 * @since 1.0
 */
public class ThreadLocalConfig {

    public static void businessMethod() {
        MyContext context = new MyContext();
        context.setName("chance");
        MyThreadLocal.set(context); // 存入数据
        try {
            // ... 核心业务逻辑 ...
            System.out.println(MyThreadLocal.get());
        } finally {
            MyThreadLocal.remove(); // ⭐ 必须在 finally 块中清除
        }
    }
}
