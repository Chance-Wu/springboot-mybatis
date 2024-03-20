package com.chance.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: ClickhouseJdbcConfig
 * @date: 2024/3/20 10:12
 * @since: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.clickhouse")
public class ClickhouseJdbcConfig {

    private String driverClassName;

    private String url;

    private Integer initialSize;

    private Integer maxActive;

    private Integer minIdle;

    private Integer maxWait;

    private String username;

    private String password;
}
