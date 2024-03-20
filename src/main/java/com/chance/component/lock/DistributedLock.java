package com.chance.component.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * <p> 分布式锁核心类 </p>
 *
 * @author chance
 * @version 1.0
 * @date 2024/3/7 15:23
 */
public class DistributedLock implements Lock {

    private static final Logger log = LoggerFactory.getLogger(DistributedLock.class);
    private Timer timer = new Timer();
    private StringRedisTemplate stringRedisTemplate;
    private String lockName;
    private String uuid;
    private Long expire = 30L;

    public DistributedLock(StringRedisTemplate stringRedisTemplate, String lockName, String uuid) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockName = lockName;
        this.uuid = uuid;
    }

    @Override
    public void lock() {
        tryLock();
    }

    @Override
    public boolean tryLock() {
        try {
            return tryLock(-1L, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("tryLock exception:", e);
        }
        return false;
    }

    // 加锁
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (-1L != time) {
            expire = unit.toSeconds(time);
        }
        String script = "if redis.call('exists',KEYS[1]) == 0 or redis.call('hexists',KEYS[1],ARGV[1]) == 1 " +
                "then " +
                "redis.call('hincrby',KEYS[1],ARGV[1],1) " +
                "redis.call('expire',KEYS[1],ARGV[2]) " +
                "return 1 " +
                "else " +
                "return 0 " +
                "end";
        String id = getId();
        // 加锁失败，循环尝试获取锁
        while (!stringRedisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), Arrays.asList(lockName), id, String.valueOf(expire))) {
            TimeUnit.MILLISECONDS.sleep(100);
        }
        // 有效期为默认时间时才启动看门狗线程
        if (-1L == time) {
            resetExpire(id);
            log.info("启动看门狗线程！");
        }
        return true;
    }

    // 解锁
    @Override
    public void unlock() {
        String script = "if redis.call('hexists',KEYS[1],ARGV[1]) == 0 " +
                "then " +
                "return nil " +
                "elseif redis.call('hincrby',KEYS[1],ARGV[1],-1) == 0 " +
                "then " +
                "return redis.call('del',KEYS[1]) " +
                "else " +
                "return 0 " +
                "end";
        Long execute = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(lockName), getId());
        if (Objects.isNull(execute)) {
            throw new IllegalMonitorStateException("this lock doesn't belong to you");
        }
        // 停止看门狗
        timer.cancel();
        log.info("释放锁成功，停止看门狗线程！");
    }

    // 拼接线程ID和UUID组成唯一标识
    public String getId() {
        return Thread.currentThread().getId() + ":" + uuid;
    }

    // 重置过期时间（延迟delay毫秒后开始执行任务，之后每间隔period毫秒执行一次任务）
    private void resetExpire(String id) {
        String script = "if redis.call('hexists',KEYS[1],ARGV[1]) == 1 " +
                "then " +
                "return redis.call('expire',KEYS[1],ARGV[2]) " +
                "else " +
                "return 0 " +
                "end";
        long time = expire * 1000 / 3;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Boolean result = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), Arrays.asList(lockName), id, String.valueOf(expire));
                log.info("重置过期时间结果：{}", result);
            }
        }, time, time);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

}