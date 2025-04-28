package com.chance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ServletContextRequestLoggingFilter;

/**
 * @author: chance
 * @date: 2024/3/25 13:11
 * @since: 1.0
 */
@Configuration
public class LoggingConfig {

    /**
     * 记录 HTTP 请求的各种信息，包括客户端信息、查询字符串、请求负载和请求头部信息，并且限制请求负载的最大长度为 64000 字节
     *
     * @return
     */
    @Bean
    public ServletContextRequestLoggingFilter requestLoggingFilter() {
        ServletContextRequestLoggingFilter filter = new ServletContextRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(true);
//        filter.setMaxPayloadLength(64000);
        return filter;
    }
}
