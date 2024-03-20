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

    private long code;
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

    public static CommonRsp<String> error(String errorMsg) {
        CommonRsp<String> rsp = new CommonRsp<>();
        rsp.setCode(ResultCode.FAIL.getCode());
        rsp.setMessage(ResultCode.FAIL.getMessage());
        rsp.setBody(errorMsg);
        return rsp;
    }

    public CommonRsp() {
    }

    public CommonRsp(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonRsp(long code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
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
