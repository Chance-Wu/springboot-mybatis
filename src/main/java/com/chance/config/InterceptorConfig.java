package com.chance.config;

import com.chance.interceptor.ApiIdempotentInterceptor;
import com.chance.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * 配置拦截器，配置类上添加了注解@Configuration，标明了该类是一个配置类并且将该类作为一个SpringBean添加到IOC容器中
 * <p>
 *
 * @author chance
 * @since 2020-09-05
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加自定义的拦截器，设置拦截的过滤路径规则
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/auth/**");
        registry.addInterceptor(apiIdempotentInterceptor());
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public ApiIdempotentInterceptor apiIdempotentInterceptor() {
        return new ApiIdempotentInterceptor();
    }
}
