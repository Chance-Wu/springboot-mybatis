package com.chance.common.annotation.sensitive;

import lombok.Data;

import java.util.function.Function;

/**
 * @description: 脱敏处理基类
 * @author: chance
 * @date: 2022/12/13 22:34
 * @since: 1.0
 */
@Data
public abstract class BaseRule implements Function<String, String> {

    /**
     * 脱敏规则对象
     */
    private RuleItem rule;

    @Override
    public String apply(String str) {
        if (null == str) {
            return null;
        }
        //初始化脱敏规则
        initRule();
        if (null == rule || null == rule.getRegex() || null == rule.getFormat()) {
            return str;
        }
        //正则替换
        return str.replaceAll(rule.getRegex(), rule.getFormat());
    }

    abstract void initRule();
}
