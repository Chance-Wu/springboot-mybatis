package com.chance.common;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-09-03
 */
public enum ResultCode {
    SUCCESS("200", "操作成功"),
    FAIL("500", "操作失败"),
    ILLEGAL_ARGUMENT("10000", "参数不合法"),
    REPETITIVE_OPERATION("10001", "请勿重复操作"),
    ;

    private String code;
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
