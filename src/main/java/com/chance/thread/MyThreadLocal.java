package com.chance.thread;

/**
 * @author chance
 * @date 2025/12/6 16:15
 * @since 1.0
 */
public class MyThreadLocal {

    private static final ThreadLocal<MyContext> CONTEXT = new ThreadLocal<>();

    public static void set(MyContext myContext) {
        CONTEXT.set(myContext);
    }

    public static MyContext get() {
        return CONTEXT.get();
    }

    public static void remove() {
        CONTEXT.remove();
    }
}
