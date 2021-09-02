package com.spring.base.utils.result;





/**
 * http请求返回的最外层对象
 * Created by crr
 */
public class Result<T> {
    /** 错误码. */
    private String returnCode="0";

    /** 提示信息. */
    private String msg="success";

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }

    /** 具体的内容. */
    private T returnData;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}

