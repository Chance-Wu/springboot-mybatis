package com.chance.controller.test;


import com.chance.common.CommonRsp;
import com.chance.common.annotation.ApiIdempotent;
import com.chance.common.converter.UserConverter;
import com.chance.component.EventContextAdaptor;
import com.chance.component.i18n.I18nUtil;
import com.chance.entity.User;
import com.chance.entity.dto.UserDto;
import com.chance.service.EmailMonitorService;
import com.chance.service.IUserService;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author chance
 * @since 2020-08-22
 */
@ApiSupport(author = "chance")
@Slf4j
@Tag(name = "测试管理类", description = "用于简单测试")
@RestController
@RequestMapping
public class TestController {

    private final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

/*    @Autowired
    private ApiIdempotentTokenService apiIdempotentTokenService;*/

    @Autowired
    private I18nUtil i18nUtil;

    @Autowired
    private EventContextAdaptor eventContextAdaptor;

    @Autowired
    private IUserService userService;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private EmailMonitorService emailMonitorService;

//    @Autowired
//    private RocketMQProducerService rocketMQProducerService;

    @ApiOperationSupport(order = 1)
    @Operation(summary = "测试国际化")
    @GetMapping("/hello")
    public CommonRsp<String> hello() {
        return CommonRsp.success(i18nUtil.get("hello.world"));
    }

    /**
     * 获取token
     */
    /*@RequestMapping("/getToken")
    public CommonRsp<String> getToken() {
        return apiIdempotentTokenService.createToken();
    }*/
    @ApiIdempotent
    @Operation(summary = "测试幂等")
    @PostMapping("/testIdempotent")
    public CommonRsp<Object> testIdempotent() {
        return CommonRsp.success();
    }

    @Operation(summary = "测试ThreadLocal")
    @Parameters(value = {@Parameter(name = "userId", description = "用户ID", required = true, in = ParameterIn.QUERY)})
    @GetMapping("/testThreadLocal")
    public CommonRsp<Map<String, String>> testThreadLocal(@RequestParam("userId") Integer userId) {

        try {
            //设置用户信息之前先查询一次ThreadLocal中的用户信息
            String before = Thread.currentThread().getName() + ":" + currentUser.get();

            //设置用户信息到ThreadLocal
            currentUser.set(userId);

            //设置用户信息之后再查询一次ThreadLocal中的用户信息
            String after = Thread.currentThread().getName() + ":" + currentUser.get();

            //汇总输出两次查询结果
            Map<String, String> result = new HashMap<>();
            result.put("before", before);
            result.put("after", after);
            return CommonRsp.success(result);
        } finally {
            //在finally代码块中删除ThreadLocal中的数据，确保数据不串
            currentUser.remove();
        }
    }

    @Operation(summary = "测试Cookie")
    @GetMapping("/cookie")
    public void cookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            log.info(String.valueOf(cookie));
        }
    }

    @Operation(summary = "测试Converter", description = "测试Converter")
    @PostMapping("/converter")
    public void converter(@Valid @RequestBody UserDto userDto) {
        User user = userConverter.targetToSource(userDto);
        log.info(">>>>>>>>{}", user.toString());
    }

    @Operation(summary = "普通body请求+Param+Header+Path")
    @Parameters(value = {
            @Parameter(name = "id", description = "文件id", in = ParameterIn.PATH),
            @Parameter(name = "token", description = "请求token", required = true, in = ParameterIn.HEADER),
            @Parameter(name = "name", description = "文件名称", required = true, in = ParameterIn.QUERY)
    })
    @PostMapping("/bodyParamHeaderPath/{id}")
    public CommonRsp<UserDto> bodyParamHeaderPath(@PathVariable("id") String id, @RequestHeader("token") String token, @RequestParam("name") String name, @RequestBody UserDto userDto) {
        userDto.setUsername(userDto.getUsername() + ",receiveName:" + name + ",token:" + token + ",pathID:" + id);
        return CommonRsp.success(userDto);
    }

//    @Operation(summary = "测试rocketmq消息发送", description = "测试rocketmq消息发送")
//    @PostMapping("/sendMQMsg")
//    public void sendMQMsg(@RequestBody UserDto userDto) {
//        User user = userConverter.targetToSource(userDto);
//        SendResult sendResult = rocketMQProducerService.sendMsg(JSON.toJSONString(user));
//        log.info(">>>>>>>>{}", JSON.toJSONString(sendResult));
//    }

    @Operation(summary = "测试mail")
    @GetMapping("/email")
    public void email(HttpServletRequest request) {
        emailMonitorService.checkEmailsForAllAccounts();
    }
}
