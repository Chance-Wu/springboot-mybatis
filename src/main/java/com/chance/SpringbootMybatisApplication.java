package com.chance;

import okhttp3.OkHttpClient;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author chance
 */
@EnableAspectJAutoProxy
@EnableAsync(proxyTargetClass = true) //开启对异步方法的支持
@EnableTransactionManagement //开启事务管理配置的支持
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        MybatisAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
public class SpringbootMybatisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootMybatisApplication.class, args);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

//        OkHttpClientFactoryBean okHttpClientFactoryBean = (OkHttpClientFactoryBean) applicationContext.getBean("&okHttpClientFactoryBean");

        OkHttpClient okHttpClient = (OkHttpClient) applicationContext.getBean("okHttpClientFactoryBean");

        try {
            System.out.println(okHttpClient.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
