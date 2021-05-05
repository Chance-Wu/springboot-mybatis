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
    private Long code;

    public BizException(Long code, String msg) {
        super(msg);
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
