package com.chance.controller.redisson;

import com.chance.common.CommonRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p> TestRedissonController </p>
 *
 * @author chance
 * @version 1.0
 * @date 2024/3/7 15:23
 */

@Api(tags = "Redis")
@RestController
@RequestMapping("/testRedisson")
@Slf4j
public class TestRedissonController {

    @Resource
    private RedissonClient redissonClient;

    // 用于Redis集群架构下，这些节点是完全独立的，所以不使用复制或任何其他隐式协调系统
    // 该对象可以用来将多个RLock对象关联为一个红锁，每个RLock对象实例可以来自于不同的Redisson实例
    @GetMapping("/testRedLock")
    @ApiOperation("红锁")
    public CommonRsp testRedLock(@RequestParam Long id) {
        String threadName = Thread.currentThread().getName();
        RLock one = redissonClient.getLock("one_" + id);
        RLock two = redissonClient.getLock("two_" + id);
        RLock three = redissonClient.getLock("three_" + id);
        RedissonMultiLock redLock = new RedissonRedLock(one, two, three);
        try {
            redLock.lock();
            log.info("{}：获得锁，开始执行业务", threadName);
            TimeUnit.SECONDS.sleep(2);
            log.info("{}：执行结束", threadName);
            return CommonRsp.success();
        } catch (Exception e) {
            log.error("testRedLock exception:", e);
            return CommonRsp.error(e.getMessage());
        } finally {
            // 注意：不能使用isLocked()和isHeldByCurrentThread()方法，会抛出UnsupportedOperationException异常
            redLock.unlock();
            log.info("{}：释放锁成功", threadName);
        }
    }

    @GetMapping("/testFairLock")
    @ApiOperation("公平锁")
    public CommonRsp testFairLock(@RequestParam Long goodsId) {
        RLock fairLock = redissonClient.getFairLock("fairLock_" + goodsId);
        String threadName = Thread.currentThread().getName();
        try {
            fairLock.lock();
            log.info("{}：获得锁，开始执行业务", threadName);
            TimeUnit.SECONDS.sleep(3);
            log.info("{}：执行结束", threadName);
            return CommonRsp.success();
        } catch (Exception e) {
            log.error("testFairLock exception:", e);
            return CommonRsp.error(e.getMessage());
        } finally {
            boolean locked = fairLock.isLocked();
            boolean heldByCurrentThread = fairLock.isHeldByCurrentThread();
            log.info("{}：获取锁状态：{} 是否当前线程保留：{}", threadName, locked, heldByCurrentThread);
            if (locked && heldByCurrentThread) {
                fairLock.unlock();
                log.info("{}：释放锁成功", threadName);
            } else {
                log.info("{}：未获得锁不用释放", threadName);
            }
        }
    }

    @GetMapping("/testRedisson")
    @ApiOperation("非公平锁")
    public CommonRsp testRedisson(@RequestParam Long goodsId) {
        RLock lock = redissonClient.getLock("lock_" + goodsId);
        String threadName = Thread.currentThread().getName();
        try {
            // 注意：若设置了锁的过期时间则没有看门狗机制

            // 阻塞，拿不到锁会一直尝试获取；锁的有效期默认30秒，有看门狗机制延长锁的有效期
            lock.lock();

/*            // 阻塞，加锁成功后设置指定的有效时间，时间到自动释放锁（无论拿到锁线程是否执行结束），前提是没有调用解锁方法；没有看门狗
            lock.lock(10, TimeUnit.SECONDS);

            // 尝试获取锁，加锁成功后启动看门狗；非阻塞，失败立马返回；注意释放锁时要判断是否存在及是否被当前线程保持
            boolean tryLock = lock.tryLock();
            if (!tryLock) {
                return CommonRsp.error("加锁失败，请稍后重试！");
            }

            // 在指定时间内尝试获取锁，失败立即返回；有看门狗
            boolean tryLock2 = lock.tryLock(5, TimeUnit.SECONDS);
            if (!tryLock2) {
                return CommonRsp.error("加锁失败，请稍后重试！");
            }

            // 指定时间内尝试获取锁，失败立即返回；成功后设置有效时间为指定值，无看门狗
            boolean tryLock1 = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (!tryLock1) {
                return CommonRsp.error("加锁失败，请稍后重试！");
            }

            // 注意：异步加锁需要调用get()方法使线程执行完成，否则会造成多个线程同时拿到锁
            RFuture<Void> voidRFuture = lock.lockAsync();
            voidRFuture.get();

            // 同lock.tryLock();
            RFuture<Boolean> booleanRFuture = lock.tryLockAsync();
            Boolean aBoolean = booleanRFuture.get();
            if (!aBoolean) {
                return CommonRsp.error("加锁失败，请稍后成重试！");
            }

            // 注意重载方法中只有一个long时，要传的是线程ID
            RFuture<Boolean> booleanRFuture1 = lock.tryLockAsync(Thread.currentThread().getId());
            Boolean aBoolean1 = booleanRFuture1.get();
            if (!aBoolean1) {
                return CommonRsp.error("加锁失败，请稍后重试！");
            }

            // 同lock.tryLock(5, TimeUnit.SECONDS)
            RFuture<Boolean> rFuture = lock.tryLockAsync(3, TimeUnit.SECONDS);
            Boolean aBoolean3 = rFuture.get();
            if (!aBoolean3) {
                return CommonRsp.error("加锁失败，请稍后重试！");
            }

            // 同lock.tryLock(5, 10, TimeUnit.SECONDS)
            RFuture<Boolean> booleanRFuture4 = lock.tryLockAsync(3, 10, TimeUnit.SECONDS);
            Boolean aBoolean4 = booleanRFuture4.get();
            if (!aBoolean4) {
                return CommonRsp.error("加锁失败，请稍后重试！");
            }

            // 原理同lock.tryLockAsync(3, 10, TimeUnit.SECONDS)，区别在于多个是线程ID的参数
            RFuture<Boolean> booleanRFuture5 = lock.tryLockAsync(3, 10, TimeUnit.SECONDS, Thread.currentThread().getId());
            Boolean aBoolean5 = booleanRFuture5.get();
            if (!aBoolean5) {
                return CommonRsp.error("加锁失败，请稍后重试！");
            }*/

            log.info("{}：获取到锁", threadName);
            TimeUnit.SECONDS.sleep(5);
            log.info("{}：业务执行结束", threadName);
        } catch (Exception e) {
            log.error("testRedisson exception:", e);
            return CommonRsp.error(e.getMessage());
        } finally {
            // 判断锁是否存在
            boolean locked = lock.isLocked();
            // 判断锁是否被当前线程保持
            boolean heldByCurrentThread = lock.isHeldByCurrentThread();
            log.info("{}：获取锁状态：{} 是否当前线程保留：{}", threadName, locked, heldByCurrentThread);
            if (locked && heldByCurrentThread) {
                lock.unlock();
                log.info("{}：释放锁", threadName);
            } else {
                log.info("{}：未获得锁不用释放", threadName);
            }
        }
        return CommonRsp.success();
    }

}
