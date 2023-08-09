package com.chance.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: SysLang
 * @author: chance
 * @date: 2023/2/27 14:06
 * @since: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLang {

    /**
     * 主键
     */
    private Long langId;

    /**
     * 语言key
     */
    private String langKey;

    /**
     * 语言value
     */
    private String langValue;

    /**
     * 语言类型（1中文 2英文）
     */
    private String langType;
}
