package com.lenovo.research.se.aiexchange.model.exception;

public abstract class BaseException extends RuntimeException {

    private final int code;

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BaseException(int code, String msg, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public String getMessage() {
        return super.getMessage();
    }

    public int getCode() {
        return this.code;
    }

    ;
}
