package com.chance.controller.distributedlock;

import com.chance.common.CommonRsp;
import com.chance.component.lock.DistributedLock;
import com.chance.component.lock.DistributedLockFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p> TestDistributedLockController </p>
 *
 * @author chance
 * @version 1.0
 * @date 2024/3/11 15:04
 */
@Slf4j
@RequestMapping("/testDistributedLockController")
@Api(tags = "可重入分布式锁")
@RestController
public class TestDistributedLockController {

    private static final String STOCK = "stock";
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private DistributedLockFactory factory;

    private void deduct() {
        // 查询库存（偷个懒，库存需手动插入）
        String cache = stringRedisTemplate.opsForValue().get(STOCK);
        if (StringUtils.isNotBlank(cache)) {
            Integer stock = Integer.valueOf(cache);
            if (stock > 0) {
                // 减库存
                stringRedisTemplate.opsForValue().set(STOCK, String.valueOf(--stock));
                log.info("扣减库存成功！");
            }
        }
    }

    @GetMapping("/test")
    @ApiOperation("测试（不重入）")
    public CommonRsp test(@RequestParam String lockName) {
        DistributedLock redisLock = factory.getRedisLock(lockName);
        redisLock.lock();
        try {
            TimeUnit.SECONDS.sleep(40);
            deduct();
        } catch (Exception e) {
            log.error("test exception:", e);
            return CommonRsp.error("失败！");
        } finally {
            redisLock.unlock();
        }
        return CommonRsp.success();
    }

    @GetMapping("/testReentrant")
    @ApiOperation("测试（重入）")
    public CommonRsp testReentrant(@RequestParam String lockName) {
        DistributedLock redisLock = factory.getRedisLock(lockName);
        redisLock.lock();
        try {
            reentrant(lockName);
            deduct();
        } catch (Exception e) {
            log.error("testReentrant exception:", e);
            return CommonRsp.error("失败！");
        } finally {
            redisLock.unlock();
        }
        return CommonRsp.success();
    }

    private void reentrant(String lockName) {
        DistributedLock redisLock = factory.getRedisLock(lockName);
        redisLock.lock();
        try {
            log.info("重入成功了！");
        } catch (Exception e) {
            log.error("reentrant exception:", e);
        } finally {
            redisLock.unlock();
        }
    }

}
