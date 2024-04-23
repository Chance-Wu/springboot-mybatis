package com.chance.component;

import com.chance.handler.RestErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

/**
 * @author: chance
 * @date: 2024/4/15 13:50
 * @since: 1.0
 */
@Configuration
public class RestTemplateConfig {

    @Value("${okhttp.connectionTimeout}")
    private int connectionTimeoutMillis;

    @Value("${okhttp.readTimeout}")
    private int readTimeoutMillis;

    @Bean("okHttp3")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(okHttp3ClientHttpRequestFactory());
        restTemplate.setErrorHandler(new RestErrorHandler());
        //添加拦截器
//        restTemplate.getInterceptors().add(getCustomInterceptor());
//        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin","adminpwd"));
        return new RestTemplate(okHttp3ClientHttpRequestFactory());
    }

    /**
     * 实现一个拦截器：使用拦截器为每一个HTTP请求添加Basic Auth认证用户名密码信息
     *
     * @return
     */
    private ClientHttpRequestInterceptor getCustomInterceptor() {
        return (httpRequest, bytes, execution) -> {
            httpRequest.getHeaders().set("authorization", "Basic " + Base64.getEncoder().encodeToString("admin:adminpwd".getBytes()));
            return execution.execute(httpRequest, bytes);
        };
    }


    @Bean
    public OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory() {
        OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeoutMillis);
        requestFactory.setReadTimeout(readTimeoutMillis);
        return requestFactory;
    }
}
