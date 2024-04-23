package com.chance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author: chance
 * @date: 2024/4/23 10:12
 * @since: 1.0
 */
@SpringBootTest
class ProxyTests {
    @Resource
    private RestTemplate restTemplate;

    @Test
    void testProxyIp() {

        String url = "http://www.httpbin.org/ip";

        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(
                new Proxy(
                        Proxy.Type.HTTP,
                        new InetSocketAddress("192.168.8.9", 8888)  //设置代理服务
                )
        );
        restTemplate.setRequestFactory(requestFactory);
        //发送请求
        String result = restTemplate.getForObject(url, String.class);
        Assertions.assertNotNull(result);
        System.out.println(result);  //打印响应结果
    }
}
