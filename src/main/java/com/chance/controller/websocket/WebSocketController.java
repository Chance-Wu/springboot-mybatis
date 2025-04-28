package com.chance.controller.websocket;

import com.chance.common.CommonRsp;
import com.chance.entity.vo.HelloMessage;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebSocketController 类用于处理 WebSocket 请求，提供与前端通过 WebSocket 进行通信的能力
 * 它使用了 Spring 的 SockJS 和 STOMP 协议来实现 WebSocket 的功能
 *
 * @author: chance
 * @date: 2024/11/10 16:20
 * @since: 1.0
 */
@ApiSupport(author = "chance")
@Slf4j
@Tag(name = "websocket", description = "用于websocket测试")
@RestController
public class WebSocketController {

    /**
     * 处理来自 "/hello" 的消息，并在处理后发送回复到 "/topic/greetings"
     * 此方法演示了如何接收客户端发送的消息，并在处理后广播给所有订阅了 "/topic/greetings" 的客户端
     *
     * @param message 从客户端接收的消息，包含客户端传递的名称信息
     * @return 返回一个通用响应对象，包含处理后的问候消息
     * @throws Exception 可能抛出的异常，例如在消息处理过程中发生的错误
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public CommonRsp<String> greeting(HelloMessage message) throws Exception {
        // 模拟处理时间，例如进行一些耗时的操作
        Thread.sleep(1000);
        // 返回处理后的消息，这里简单地拼接了一个问候语
        return CommonRsp.success("Hello, " + message.getName() + "!");
    }
}
