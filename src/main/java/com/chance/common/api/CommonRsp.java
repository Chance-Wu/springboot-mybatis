package com.chance.common.api;

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
