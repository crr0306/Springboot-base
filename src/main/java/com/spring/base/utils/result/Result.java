package com.spring.base.utils.result;





/**
 * http请求返回的最外层对象
 * Created by crr
 */
public class Result<T> {
    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(T result) {
        this.result = result;
    }

    /** 错误码. */
    private int code=200;

    /** 提示信息. */
    private String message="success";

    /** 具体的内容. */
    private T result;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    private boolean success=true;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }
}

