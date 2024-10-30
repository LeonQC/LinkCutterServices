package com.llh.enums;

public enum ResultCode implements ErrorCode{

    SUCCESS(10000,"操作成功"),
    INVALID_PARAMETER(10001,"参数错误"),
    RESOURCE_NOT_FOUND(10003,"资源不存在"),
    INSUFFICIENT_PERMISSIONS(10004,"访问权限不足"),
    NETWORK_ERROR(10005,"网络错误"),
    TIMEOUT_ERROR(10006,"超时错误"),
    DATABASE_ERROR(10007,"数据库操作错误"),
    USER_SETUP_ERROR(10008, "新增用户错误"),
    CHANGE_PASSWORD_ERROR(10009, "修改密码错误"),
    USER_NOT_EXIST(10010, "用户名或密码错误"),
    USERID_ERROR(10011, "用户ID不存在"),
    PHONE_NUMBER_ERROR(10012, "手机号码格式有误"),
    PHONE_NUMBER_NOT_EXIST(10013, "手机号码不存在"),
    HIGH_SMS_REQUEST_RATE_ERROR(10014, "发送频率过高，请稍后再试"),
    SMSCODE_SEND_ERROR(10015, "发送失败"),
    SMSCODE_USED_ERROR(10016, "请勿重复使用验证码"),
    SMSCODE_EXPIRED_ERROR(10017, "验证码已过期"),
    SMSCODE_ERROR(10018, "验证码错误"),
    FILE_WRITE_ERROR(10019, "文件写入有误"),
    FILE_READ_ERROR(10020, "文件读取有误"),
    DATA_TRANSFER_ERROR(10021, "数据转换失败"),
    OBJECT_NOT_FOUND(100022, "对象不存在"),




    URL_FORMAT_ERROR(20001, "链接格式有误！"),
    ;

    private final Integer code;

    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
