package com.chance.component.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * @author chance
 * @date 2026/5/4 14:22
 * @since 1.0
 */
@Component
public class AiLogInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AiLogInterceptor.class);

    public String log(String input, Supplier<String> action) {
        long start = System.currentTimeMillis();
        String result = action.get();
        long cost = System.currentTimeMillis() - start;

        LOGGER.info("AI请求: {}", input);
        LOGGER.info("AI响应: {}", result);
        LOGGER.info("耗时: {} ms", cost);
        return result;
    }
}
