package com.ethan.common.response;

public enum ResponseState implements IResponseState {

    SUCCESS(true, 10000, "操作成功"),
    MSG_CODE_SEND_SUCCESS(true, 10001, "验证码发送成功"),
    SIGNIN_SUCCESS(true, 10002, "注册成功"),
    LOGIN_SUCCESS(true, 10003, "登录成功"),
    UPLOAD_SUCCESS(true, 10004, "上传成功"),
    DOWNLOAD_SUCCESS(true, 10005, "下载成功"),
    THIRD_PARTY_ACCESS_SUCCESS(true, 10006, "第三方应用获取数据成功"),

    FAIL(false, 20000, "操作失败"),
    LOGIN_FAILED(false, 20001, "账号或密码错误"),
    PHONE_NUMBER_ERROR(false, 20002, "手机号码错误"),
    MSG_CODE_SEND_FAILED(false, 20003, "验证码发送失败，请稍后重试"),
    MSG_CODE_SEND_TOO_MUCH(false, 20004, "操作太过频繁，请稍后重试"),
    SIGNIN_FAILED(false, 20005, "注册失败，请稍后重试"),
    SIGNIN_INFO_ERROR(false, 20006, "注册信息错误"),
    MSG_CODE_ERROR(false, 20007, "验证码错误"),
    PHONE_NUMBER_NULL(false, 20008, "手机号码为空"),
    USER_NOT_REGISTER(false, 20009, "该手机号码未注册"),
    ACCOUNT_NOT_LOGIN(false, 20010, "账号未登录"),
    UPLOAD_FAILED(false, 20011, "上传失败"),
    LOCAL_CREATE_FAILED(false, 20012, "创建文件失败"),
    DOWNLOAD_FAILED(false, 20013, "下载失败"),
    EMAIL_EXIST(false, 20014, "邮箱已被注册"),
    USERNAME_EXIST(false, 20015, "用户名已被注册"),
    MSG_CODE_OUT_OF_DATE(false, 20016, "验证码过期"),
    LOGIN_INFO_ERROR(false, 20017, "登录信息错误"),
    PERMISSION_DENIED(false, 20018, "无权限"),
    THIRD_PARTY_ACCESS_FAIL(false, 20019, "第三方应用未接入统一用户中心");

    private final boolean success;
    private final int code;
    private String msg;

    ResponseState(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public ResponseState setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
