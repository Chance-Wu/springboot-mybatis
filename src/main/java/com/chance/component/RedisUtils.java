package com.chance.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * redis工具类
 * <p>
 *
 * @author chance
 * @since 2020-09-05
 */
@Component
public class RedisUtils {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将value对象写入缓存
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        if (null != key) {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 指定缓存的失效时间
     *
     * @param key
     * @param exp
     */
    public void expire(String key, long exp) {
        if (exp > 0) {
            redisTemplate.expire(key, exp, TimeUnit.SECONDS);
        }
    }

    /**
     * 将value对象写入缓存，并指定过期时间
     *
     * @param key
     * @param value
     * @param exp
     */
    public void set(String key, Object value, long exp) {
        if (null != key) {
            set(key, value);
            expire(key, exp);
        }
    }

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    public Boolean del(String key) {
        if (exists(key)) {
            return redisTemplate.delete(key);
        } else {
            logger.error("del key:{}", key + " 不存在");
            return false;
        }

    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public Boolean exists(String key) {
        Boolean exists = redisTemplate.hasKey(key);
        logger.info("exists key:{} hasKey:{}", key, exists);
        return exists;
    }
}
