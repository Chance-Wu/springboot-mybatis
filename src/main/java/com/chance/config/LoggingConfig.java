//package com.chance.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.filter.ServletContextRequestLoggingFilter;
//
/// **
// * @author: chance
// * @date: 2024/3/25 13:11
// * @since: 1.0
// */
//@Configuration
//public class LoggingConfig {
//
//    /**
//     * 记录 HTTP 请求的各种信息，包括客户端信息、查询字符串、请求负载和请求头部信息，并且限制请求负载的最大长度为 64000 字节
//     *
//     * @return
//     */
//    @Bean
//    public ServletContextRequestLoggingFilter requestLoggingFilter() {
//        ServletContextRequestLoggingFilter filter = new ServletContextRequestLoggingFilter();
////        filter.setIncludeClientInfo(true);
//        // 包含查询参数
//        filter.setIncludeQueryString(true);
//        // 包含请求体
//        filter.setIncludePayload(true);
//        // 包含请求头
//        filter.setIncludeHeaders(true);
//        // 限制请求体日志长度（避免大字段溢出）
//        filter.setMaxPayloadLength(1024);
//        filter.setAfterMessagePrefix("[REQUEST DATA] ");
//        return filter;
//    }
//}
