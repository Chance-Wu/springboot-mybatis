package com.chance.controller;


import com.chance.common.CommonRsp;
import com.chance.common.annotation.ApiIdempotent;
import com.chance.component.EventContextAdaptor;
import com.chance.component.i18n.I18nUtil;
import com.chance.entity.User;
import com.chance.entity.dto.UserDto;
import com.chance.service.ApiIdempotentTokenService;
import com.chance.service.IUserService;
import com.chance.service.UnifiedService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@Slf4j
@Api("TestController")
@RestController
@RequestMapping
public class TestController {

    private final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    @Autowired
    private ApiIdempotentTokenService apiIdempotentTokenService;

    @Autowired
    private I18nUtil i18nUtil;

    @Autowired
    private EventContextAdaptor eventContextAdaptor;

    @Autowired
    private IUserService userService;

    @GetMapping("/hello")
    public String hello() {
        return i18nUtil.get("hello.world");
    }

    /**
     * 获取token
     */
    @RequestMapping("/getToken")
    public CommonRsp<String> getToken() {
        return apiIdempotentTokenService.createToken();
    }

    /**
     * 测试接口幂等性, 在需要幂等性校验的方法上声明此注解即可
     */
    @ApiIdempotent
    @RequestMapping("/testIdempotent")
    public CommonRsp<Object> testIdempotent() {
        return CommonRsp.success();
    }


    @GetMapping("/wrong1")
    public Map<String,String> wrong(@RequestParam("userId") Integer userId) {

        try {
            //设置用户信息之前先查询一次ThreadLocal中的用户信息
            String before = Thread.currentThread().getName() + ":" + currentUser.get();

            //设置用户信息到ThreadLocal
            currentUser.set(userId);

            //设置用户信息之后再查询一次ThreadLocal中的用户信息
            String after = Thread.currentThread().getName() + ":" + currentUser.get();

            //汇总输出两次查询结果
            Map<String,String> result = new HashMap<>();
            result.put("before", before);
            result.put("after", after);
            return result;
        } finally {
            //在finally代码块中删除ThreadLocal中的数据，确保数据不串
            currentUser.remove();
        }
    }


    @GetMapping("/cookie")
    public void cookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            log.info(String.valueOf(cookie));
        }
    }

    @GetMapping("/unified")
    public void event() {
        UnifiedService memberService = eventContextAdaptor.getEventServiceByType("memberEvent");
        String res1 = memberService.executeEvent();
        log.info(res1);
        UnifiedService couponService = eventContextAdaptor.getEventServiceByType("couponEvent");
        String res2 = couponService.executeEvent();
        log.info(res2);
    }
}
