package com.spring.base.utils.result;

public class ResultUtil {

    public static Result success(Object object) {
        Result result = new Result();
        result.setReturnCode("0");
        result.setMsg("成功");
        result.setReturnData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setReturnCode(code);
        result.setMsg(msg);
        return result;
    }
}

