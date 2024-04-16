package com.chance.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

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
        return new RestTemplate(okHttp3ClientHttpRequestFactory());
    }

    @Bean
    public OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory() {
        OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeoutMillis);
        requestFactory.setReadTimeout(readTimeoutMillis);
        return requestFactory;
    }
}
