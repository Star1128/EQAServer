package com.ethan.common.response;

import lombok.Data;

@Data
public class ResponseResult {

    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public ResponseResult(IResponseState state) {
        this.success = state.isSuccess();
        this.code = state.getCode();
        this.msg = state.getMsg();
    }

    public ResponseResult(IResponseState state, Object data) {
        this.success = state.isSuccess();
        this.code = state.getCode();
        this.msg = state.getMsg();
        this.data = data;
    }

    /**
     * 成功状态的自定义扩展
     */
    public static ResponseResult SUCCESS(String msg) {
        ResponseResult result = new ResponseResult(ResponseState.SUCCESS);
        result.setMsg(msg);
        return result;
    }

    public static ResponseResult SUCCESS(String msg, Object data) {
        ResponseResult result = new ResponseResult(ResponseState.SUCCESS);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * 失败状态的自定义扩展
     */
    public static ResponseResult FAIL(String msg) {
        ResponseResult result = new ResponseResult(ResponseState.FAIL);
        result.setMsg(msg);
        return result;
    }

    public static ResponseResult FAIL(String msg, Object data) {
        ResponseResult result = new ResponseResult(ResponseState.FAIL);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
