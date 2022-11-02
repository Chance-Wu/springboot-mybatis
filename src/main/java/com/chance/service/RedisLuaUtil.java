package com.chance.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: RedisLuaUtil
 * @author: chance
 * @date: 2022/10/20 15:30
 * @since: 1.0
 */
@Slf4j
@Service
public class RedisLuaUtil {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /*
    run a lua script
    luaFileName: lua file name,no path
    keyList: list for redis key
    return:lua return value,type is string
    */
    public String runLuaScript(String luaFileName, List<String> keyList) {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/" + luaFileName)));
        redisScript.setResultType(String.class);

        String argsone = "none";
        //String result = stringRedisTemplate.execute(redisScript, keyList,argsone);
        String result = "";
        try {
            result = stringRedisTemplate.execute(redisScript, keyList, argsone);
        } catch (Exception e) {
            log.error(">>>>>>>>发生异常", e);
        }

        return result;
    }
}
