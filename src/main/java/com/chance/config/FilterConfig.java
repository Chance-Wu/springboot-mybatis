//package com.chance.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.filter.DelegatingFilterProxy;
//
/// **
// * @author chance
// * @date 2025/7/15 15:06
// * @since 1.0
// */
//@Configuration
//public class FilterConfig {
//
//    @Bean
//    public FilterRegistrationBean<DelegatingFilterProxy> sessionFilter() {
//        FilterRegistrationBean<DelegatingFilterProxy> registration = new FilterRegistrationBean<>();
//        DelegatingFilterProxy filter = new DelegatingFilterProxy("springSessionRepositoryFilter");
//        registration.setFilter(filter);
//        registration.addUrlPatterns("/*");
//        registration.setName("springSessionRepositoryFilter");
//        registration.setOrder(1); // 设置执行顺序
//        return registration;
//    }
//}
