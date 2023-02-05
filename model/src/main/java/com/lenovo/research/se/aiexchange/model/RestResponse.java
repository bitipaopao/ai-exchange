package com.lenovo.research.se.aiexchange.model;

import com.lenovo.research.se.aiexchange.model.enums.ErrorCode;

@SuppressWarnings("unused")
public class RestResponse<T> {

    private int code;

    private String msg;

    private T data;

    public int getCode() {
        return code;
    }

    public RestResponse<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RestResponse<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RestResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static <T> RestResponse<T> successWith(T data) {
        return new RestResponse<T>().setData(data)
                .setCode(ErrorCode.Success.getCode())
                .setMsg(ErrorCode.Success.getMessage());
    }

    public static <T> RestResponse<T> successWith(T data, ResponsePage page) {
        return new RestResponse<T>().setData(data)
                .setCode(ErrorCode.Success.getCode())
                .setMsg(ErrorCode.Success.getMessage());
    }

    public static <T> RestResponse<T> failWith(ErrorCode resultErrorCode) {
        return new RestResponse<T>().setCode(resultErrorCode.getCode())
                .setMsg(resultErrorCode.getMessage());
    }

    public boolean success() {
        return this.getCode() == ErrorCode.Success.getCode();
    }
}
