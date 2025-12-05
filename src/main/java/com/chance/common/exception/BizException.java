package com.chance.common.exception;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-09-03
 */
public class BizException extends RuntimeException {

    /**
     * 自定义错误码
     */
    private String code;

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
