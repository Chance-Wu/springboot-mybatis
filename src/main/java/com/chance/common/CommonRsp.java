package com.chance.common;

/**
 * <p>
 *
 * <p>
 *
 * @author chance
 * @since 2020-09-03
 */
public class CommonRsp<T> {

    private String code;
    private String message;
    private T body;

    public static CommonRsp<Object> success() {
        CommonRsp<Object> rsp = new CommonRsp<>();
        rsp.setCode(ResultCode.SUCCESS.getCode());
        rsp.setMessage(ResultCode.SUCCESS.getMessage());
        return rsp;
    }

    public static <T> CommonRsp<T> success(T body) {
        CommonRsp<T> rsp = new CommonRsp<>();
        rsp.setCode(ResultCode.SUCCESS.getCode());
        rsp.setMessage(ResultCode.SUCCESS.getMessage());
        rsp.setBody(body);
        return rsp;
    }

    public static <T> CommonRsp<T> error(String errorMsg) {
        CommonRsp<T> rsp = new CommonRsp<>();
        rsp.setCode(ResultCode.FAIL.getCode());
        rsp.setMessage(errorMsg);
        return rsp;
    }

    public CommonRsp() {
    }

    public CommonRsp(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonRsp(String code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
