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
    private Integer code;

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
