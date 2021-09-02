package com.spring.base.utils.exception;

import com.spring.base.utils.errorcode.ServiceError;
import com.spring.base.utils.exception.WeiDianServiceException;
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
        if (e instanceof WeiDianServiceException) {   //判断异常是否是我们定义的异常
            WeiDianServiceException weiDianServiceException = (WeiDianServiceException) e;
            result.setCode(weiDianServiceException.getErrorCode());
            result.setMessage(weiDianServiceException.getErrorMsg());
            result.setSuccess(false);
            return result;
        }else {
            logger.error("the error : {}",e.getMessage(),e);
            result.setCode(ServiceError.SYSTEM_ERROR_CODE);
            result.setMessage(ServiceError.SYSTEM_ERROR_MSG);
            result.setSuccess(false);
            return result;
        }
    }
}


