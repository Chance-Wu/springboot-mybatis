package com.chance.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: chance
 * @date: 2024/4/30 14:44
 * @since: 1.0
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("api")
                .displayName("learning")
                .pathsToMatch("/**")
                .build();
    }

}
