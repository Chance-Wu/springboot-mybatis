package com.chance.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p> JsonUtil </p>
 *
 * @author chance
 * @date 2023/5/3 14:05
 * @since 1.0
 */
public class JsonUtil {

    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
    }

    private JsonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

    public static String toJsonString(Object obj) {
        try {
            if (obj != null) {
                return mapper.writeValueAsString(obj);
            }
        } catch (Exception e) {
            logger.warn("Cannot convert to json " + obj);
        }
        return "{}";
    }
}
