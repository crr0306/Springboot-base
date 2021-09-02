package com.spring.base.utils.exception;


import com.spring.base.utils.errorcode.ServiceError;
import com.spring.base.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 捕获异常的类，返回信息给浏览器，可以自定义返回的code,msg等信息*/


@ControllerAdvice
public class ExceptionHandle {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        Result result=new Result();
        if (e instanceof ServiceException) {   //判断异常是否是我们定义的异常
            ServiceException serviceException = (ServiceException) e;
            result.setReturnCode(serviceException.getErrorCode());
            result.setMsg(serviceException.getErrorMsg());
            return result;
        }else {
            logger.error("【系统异常】:"+e);
            result.setReturnCode(ServiceError.SYSTEM_ERROR.getCode());
            result.setMsg(ServiceError.SYSTEM_ERROR.getMsg());
            return result;
        }
    }
}


