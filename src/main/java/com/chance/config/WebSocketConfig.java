package com.chance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket配置类，用于配置WebSocket的连接和消息代理
 * 实现WebSocketMessageBrokerConfigurer接口以配置消息代理和STOMP协议的端点
 *
 * @author: chance
 * @date: 2024/11/10 15:55
 * @since: 1.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 配置消息代理
     *
     * @param config MessageBrokerRegistry对象，用于配置消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用简单的内存消息代理，用于广播消息
        config.enableSimpleBroker("/topic");
        // 设置应用前缀，所有发送到服务器的消息都必须以 "/app" 开头
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * 注册STOMP协议的端点
     *
     * @param registry StompEndpointRegistry对象，用于注册STOMP协议的端点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册一个 STOMP 终端点，客户端可以通过这个终端点连接到 WebSocket
        // .withSockJS() 表示使用 SockJS 协议，提供对不支持 WebSocket 的浏览器的回退机制
        registry
                .addEndpoint("/websocket-example")
                .setAllowedOrigins("*")
                .withSockJS();
    }

}
