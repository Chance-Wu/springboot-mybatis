package com.chance.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @description: CustomDoubleSerialize
 * @author: chance
 * @date: 2022/12/14 01:16
 * @since: 1.0
 */
public class CustomDoubleSerialize extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        }
    }
}
