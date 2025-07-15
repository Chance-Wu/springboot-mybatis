package com.chance.util.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * ZooKeeper 分布式锁工具类
 */
public class ZkDistributedLock {

    // ZooKeeper 连接地址（多个地址用逗号分隔）
    private static final String ZK_CONNECT_STRING = "127.0.0.1:2181";
    // 会话超时时间（毫秒）
    private static final int SESSION_TIMEOUT_MS = 30000;
    // 连接超时时间（毫秒）
    private static final int CONNECTION_TIMEOUT_MS = 30000;

    // Curator 客户端
    private static final CuratorFramework CLIENT = CuratorFrameworkFactory.builder()
            .connectString(ZK_CONNECT_STRING)
            .sessionTimeoutMs(SESSION_TIMEOUT_MS)
            .connectionTimeoutMs(CONNECTION_TIMEOUT_MS)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    // 缓存每个锁路径对应的 InterProcessMutex 实例
    private static final ConcurrentHashMap<String, InterProcessMutex> LOCK_MAP = new ConcurrentHashMap<>();

    static {
        CLIENT.start();
    }

    /**
     * 获取一个分布式锁
     *
     * @param lockPath 锁的路径，例如 "/locks/my_lock"
     * @return 是否成功获取锁
     * @throws Exception
     */
    public static boolean lock(String lockPath) throws Exception {
        return lock(lockPath, -1, null);
    }

    /**
     * 尝试获取一个分布式锁（可设置超时时间）
     *
     * @param lockPath 锁的路径
     * @param time     等待时间
     * @param unit     时间单位
     * @return 是否成功获取锁
     * @throws Exception
     */
    public static boolean lock(String lockPath, long time, TimeUnit unit) throws Exception {
        InterProcessMutex lock = LOCK_MAP.computeIfAbsent(lockPath, k -> new InterProcessMutex(CLIENT, k));

        if (unit == null) {
            return lock.acquire(0, TimeUnit.MILLISECONDS);
        } else {
            return lock.acquire(time, unit);
        }
    }

    /**
     * 释放锁
     *
     * @param lockPath 锁的路径
     * @throws Exception
     */
    public static void unlock(String lockPath) throws Exception {
        InterProcessMutex lock = LOCK_MAP.get(lockPath);
        if (lock != null && lock.isAcquiredInThisProcess()) {
            lock.release();
        }
    }

    /**
     * 关闭客户端连接（应用关闭时调用）
     */
    public static void close() {
        CLIENT.close();
    }
}