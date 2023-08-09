package com.chance;

import com.chance.component.CustomResourceLoader;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

/**
 * @author chance
 */
@EnableAspectJAutoProxy
//开启对异步方法的支持
@EnableAsync(proxyTargetClass = true)
//开启事务管理配置的支持
@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        MybatisAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
public class SpringbootMybatisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringbootMybatisApplication.class, args);

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        CustomResourceLoader customResourceLoader = (CustomResourceLoader) applicationContext.getBean("customResourceLoader");
        try {
            customResourceLoader.showResourceData("https://www.clc.plus/fqa/100067.html");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
