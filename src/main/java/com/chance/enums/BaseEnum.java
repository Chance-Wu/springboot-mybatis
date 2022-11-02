package com.chance.enums;

/**
 * @description: 枚举类基类
 * @author: chance
 * @date: 2022/9/3 13:41
 * @since: 1.0
 */
public interface BaseEnum<E extends Enum<?>, T> {

    /**
     * 真正与数据库进行映射的值
     * @return
     */
    T getKey();

    /**
     * 显示的信息
     * @return
     */
    String getValue(String key);
}
