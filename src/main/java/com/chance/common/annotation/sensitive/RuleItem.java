package com.chance.common.annotation.sensitive;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: RuleItem
 * @author: chance
 * @date: 2022/12/13 22:35
 * @since: 1.0
 */
@Data
@Accessors(chain = true)
public class RuleItem {

    /**
     * 正则
     */
    private String regex;

    /**
     * 格式化显示
     */
    private String format;
}
