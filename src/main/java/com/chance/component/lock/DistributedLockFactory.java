package com.chance.component.lock;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p> 分布式锁工厂类 </p>
 *
 * @author chance
 * @version 1.0
 * @date 2024/3/11 15:02
 */
@Component
public class DistributedLockFactory {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String uuid;

    public DistributedLockFactory() {
        this.uuid = UUID.randomUUID().toString().replace("-", "");
    }

    public DistributedLock getRedisLock(String lockName) {
        return new DistributedLock(stringRedisTemplate, lockName, uuid);
    }
}
