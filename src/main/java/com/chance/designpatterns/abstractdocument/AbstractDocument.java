package com.chance.designpatterns.abstractdocument;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author chance
 * @date 2024/11/28 14:14
 * @since 1.0
 */
public class AbstractDocument implements Document {

    private final Map<String, Object> properties;

    public AbstractDocument(Map<String, Object> properties) {
        Objects.requireNonNull(properties, "Properties must be provided");
        this.properties = properties;
    }

    @Override
    public void put(String key, Object value) {
        properties.put(key, value);
    }

    @Override
    public Object get(String key) {
        return properties.get(key);
    }

    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        // 获取指定键下的所有值
        Object value = get(key);
        if (value instanceof List) {
            // 如果值是列表，则尝试将每个元素转换为Map，并应用构造函数
            List<?> list = (List<?>) value;
            return list.stream()
                    .filter(Map.class::isInstance)
                    .map(el -> (Map<String, Object>) el)
                    .map(constructor);
        } else {
            // 如果不是列表或没有找到键，则返回空流
            return Stream.empty();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getName()).append("[");
        properties.forEach((key, value) ->
                builder.append("[").append(key).append(" : ")
                        .append(value).append("]")
        );
        builder.append("]");
        return builder.toString();
    }
}
