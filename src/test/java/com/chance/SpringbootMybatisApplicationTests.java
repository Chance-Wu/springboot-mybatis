package com.chance;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest
class SpringbootMybatisApplicationTests {

    @Test
    void contextLoads() {
    }

    private final static String lockKey = "lock";
    private final static int waitTimeout = 3;
    private final static int leaseTime = 10;

    @Test
    void redissonTest() {
        // 1.构造redis配置
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
//                .setPassword("")
                .setDatabase(0);

        // 2.创建redisson客户端
        RedissonClient redissonClient = Redisson.create(config);
        // 3.获取锁对象实例（无法保证是按线程的顺序获取到）
        RLock rLock = redissonClient.getLock(lockKey);

        try {
            /**
             * 4.尝试获取锁
             * waitTimeout 尝试获取锁的最大等待时间，超过这个值，则认为获取锁失败
             * leaseTime   锁的持有时间,超过这个时间锁会自动失效（值应设置为大于业务处理的时间，确保在锁有效期内业务能处理完）
             */
            boolean res = rLock.tryLock((long) waitTimeout, (long) leaseTime, TimeUnit.SECONDS);
            if (res) {
                log.info("成功获得锁，在这里处理业务。。。");
            }
        } catch (Exception e) {
            throw new RuntimeException("aquire lock fail");
        } finally {
            // 5.无论如何, 最后都要解锁
            rLock.unlock();
        }
    }
}
