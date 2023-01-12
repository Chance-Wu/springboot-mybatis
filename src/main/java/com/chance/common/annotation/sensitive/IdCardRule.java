package com.chance.common.annotation.sensitive;

/**
 * @description: 身份证号码处理类
 * @author: chance
 * @date: 2022/12/13 22:49
 * @since: 1.0
 */
public class IdCardRule extends BaseRule {

    /**
     * 仅显示前6位和后4位
     */
    @Override
    void initRule() {
        setRule(new RuleItem()
                .setRegex("(\\d{6})\\d*(\\w{4})")
                .setFormat("$1********$2"));
    }
}
