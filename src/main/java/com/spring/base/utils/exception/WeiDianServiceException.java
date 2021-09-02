package com.spring.base.utils.exception;

import java.text.MessageFormat;

/**
 * 功能描述: <br>
 * 〈〉只有继承RuntimeException才会进行事务回滚，Exception不会进行事务回滚
 * @Param:
 * @Return:
 * @Author: chengranran
 * @Date: 2020/11/19 16:15
 */

public class WeiDianServiceException extends RuntimeException {
    private static final long serialVersionUID = -5986531402783126618L;

    /**
     * 自定义错误代码
     */
    private int errorCode;

    /**
     * 自定义错误信息
     */
    private String errorMsg;



    public WeiDianServiceException(final int errorCode, final String errorMsg ) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;


    }

    public WeiDianServiceException(final int errorCode, final String errorMsg, Object... message) {
        super();
        this.errorCode = errorCode;
        this.errorMsg = MessageFormat.format(errorMsg, message);
    }

    public WeiDianServiceException(final String message) {
        super(message);
    }

    public WeiDianServiceException(final Throwable cause) {
        super(cause);
    }

    public WeiDianServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.getMessage();
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return (errorMsg == null || errorMsg.equals("")) ? super.getMessage() : this.errorMsg;
    }
}
