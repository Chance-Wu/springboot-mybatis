package com.chance.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * <p>
 * 数据源配置类
 *
 * 配置了主数据源mysql
 * <p>
 *
 * @author chance
 * @since 2020-08-15
 */
@Configuration
public class DatasourceConfig {

    @Bean("primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
