package com.chance.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> RedissonConfig </p>
 *
 * @author chance
 * @version 1.0
 * @date 2024/3/7 15:19
 */
@Configuration
@EnableCaching
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        /*
           连接哨兵：config.useSentinelServers().setMasterName("myMaster").addSentinelAddress()
           连接集群：config.useClusterServers().addNodeAddress()
           连接主从：config.useMasterSlaveServers().setMasterAddress("xxx").addSlaveAddress("xxx")
         */

        // 连接单机
        config.useSingleServer()
                .setAddress("redis://" + this.host + ":" + this.port);
        return Redisson.create(config);
    }

}
