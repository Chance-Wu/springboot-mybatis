package com.chance.common.annotation.sensitive;

/**
 * @description: 姓名脱敏处理类
 * @author: chance
 * @date: 2022/12/13 22:54
 * @since: 1.0
 */
public class UserNameRule extends BaseRule {

    /**
     * 仅显示第一个汉字
     */
    @Override
    void initRule() {
        setRule(new RuleItem()
                .setRegex("(\\S)\\S*")
                .setFormat("$1**"));
    }
}
