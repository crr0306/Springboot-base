package com.spring.base.utils.errorcode;

/**
 * 错误码
 * 421000-421100,通用错误码
 * 421101开始,其他错误码
 */
public enum ServiceError {
    /** =================================通用错误start================================= **/




    /** =================================通用错误end================================= **/

    /** =================================其他相关错误start================================= **/

    SING_REPEAT("430001", "重复签到"),
    GET_AC_DETAIL_FAILURE("430002", "此活动详情获取失败"),
    SIGN_TIME_NOT_START("430003", "签到时间还未开始"),
    SIGN_TIME_END("430004", "签到时间已结束"),


    AC_INSERT_FAILURE("430005", "活动写入失败"),
    CONNECT_MINI_PROGRAM_FAILURE("430006", "连接小程序失败"),
    GET_AUTH_PHONE_FAILURE("430007", "获取授权手机号失败"),
    /** =================================其他相关错误end================================= **/
    SESSION_EXPIRE("421001", "会话已过期"),
    MISS_PARAM("421002", "缺少参数"),
    PARAM_VALUE_ERROR("421003", "参数不正确"),
    MISS_TOKEN("421004", "缺少登录令牌"),
    SYSTEM_ERROR("421000", "系统错误");

    private String code;

    public String getCode() {
        return code;
    }

    private String msg;





    ServiceError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    public String getMsg() {
        return this.msg;
    }
}
