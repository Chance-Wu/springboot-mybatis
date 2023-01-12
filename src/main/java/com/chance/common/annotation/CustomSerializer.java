package com.chance.common.annotation;

import com.chance.common.annotation.sensitive.BaseRule;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * @description: 嵌入式转译注解
 * @description: 生效点：request请求完成返回时，进行序列化过滤，并嵌入实现
 * @description: 失效点：service业务处理层是不会生效的，序列化对象转Json，或者其他格式对象后，也是无法过滤到的
 * @author: chance
 * @date: 2022/12/13 00:02
 * @since: 1.0
 */
@JacksonAnnotationsInside
@JsonSerialize(using = MyJsonSerializer.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CustomSerializer {

    /**
     * 脱敏规则处理类
     *
     * @return
     */
    Class<? extends BaseRule> value() default BaseRule.class;

    /**
     * 正则，pattern和format必需同时有值。如果都有值时，优先使用正则进行规则替换
     *
     * @return
     */
    String pattern() default "";

    String format() default "";

}