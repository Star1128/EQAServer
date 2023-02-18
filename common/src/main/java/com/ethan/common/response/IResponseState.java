package com.ethan.common.response;

public interface IResponseState {

    boolean isSuccess();

    int getCode();

    String getMsg();

    ResponseState setMsg(String msg);
}
