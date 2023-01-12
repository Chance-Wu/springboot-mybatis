package com.chance.common.annotation.sensitive;

/**
 * @description: 手机号脱敏处理类
 * @author: chance
 * @date: 2022/12/13 22:42
 * @since: 1.0
 */
public class PhoneRule extends BaseRule {

    /**
     * 仅显示前3位和后4位
     */
    @Override
    void initRule() {
        setRule(new RuleItem()
                .setRegex("(\\d{3})\\d*(\\d{4})")
                .setFormat("$1****$2"));
    }
}
