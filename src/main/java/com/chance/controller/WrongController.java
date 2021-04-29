package com.chance.controller;


import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Slf4j
@Api("WrongController")
@RestController
@RequestMapping
public class WrongController {

    private ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    @GetMapping("/wrong1")
    public Map wrong(@RequestParam("userId") Integer userId) {

        try {
            //设置用户信息之前先查询一次ThreadLocal中的用户信息
            String before = Thread.currentThread().getName() + ":" + currentUser.get();

            //设置用户信息到ThreadLocal
            currentUser.set(userId);

            //设置用户信息之后再查询一次ThreadLocal中的用户信息
            String after = Thread.currentThread().getName() + ":" + currentUser.get();

            //汇总输出两次查询结果
            Map result = new HashMap();
            result.put("before", before);
            result.put("after", after);
            return result;
        } finally {
            //在finally代码块中删除ThreadLocal中的数据，确保数据不串
            currentUser.remove();
        }
    }

    // 线程个数
    private static int THREAD_COUNT = 10;
    // 总元素数量
    private static int ITEM_COUNT = 1000;

}
