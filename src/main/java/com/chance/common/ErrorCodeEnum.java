package com.chance.common;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-09-03
 */
public enum ErrorCodeEnum implements IErrorCode {
    SUCCESS("200", "操作成功"),
    FAIL("500", "操作失败"),
    ILLEGAL_ARGUMENT("10000", "参数不合法"),
    REPETITIVE_OPERATION("10001", "请勿重复操作"),
    NO_DATA("10002", "数据不存在"),
    ;

    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
