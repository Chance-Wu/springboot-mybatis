package com.chance.enums;

/**
 * @description: 性别枚举
 * @author: chance
 * @date: 2022/9/3 13:41
 * @since: 1.0
 */
public enum SexEnum {
    DEFAULT("0", ""),
    MAN("1", "男"),
    WOMAN("2", "女");


    private String code;
    private String msg;

    SexEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static SexEnum getSexEnumByCode(String code) {
        for (SexEnum sexEnum : SexEnum.values()) {
            if (sexEnum.getCode().equals(code)) {
                return sexEnum;
            }
        }
        return null;
    }
}
