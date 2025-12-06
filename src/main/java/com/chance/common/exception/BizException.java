package com.chance.common.exception;

import com.chance.common.IErrorCode;

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
     * 关键：持有 ErrorCodeEnum 实例，而非重复存储 code 和 message
     */
    private final IErrorCode errorCodeEnum;

    /**
     * 构造只接收 IErrorCode
     *
     * @param errorCodeEnum 错误码
     */
    public BizException(IErrorCode errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.errorCodeEnum = errorCodeEnum;
    }

    public BizException(IErrorCode errorCodeEnum, Throwable cause) {
        super(errorCodeEnum.getMessage(), cause);
        this.errorCodeEnum = errorCodeEnum;
    }

    public BizException(IErrorCode errorCodeEnum, String message) {
        super(message);
        this.errorCodeEnum = errorCodeEnum;
    }

    public String getCode() {
        return this.errorCodeEnum.getCode();
    }

}
