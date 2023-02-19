package com.shinner.data.aiexchange.model.exception;

import com.shinner.data.aiexchange.model.enums.ErrorCode;

public class InternalException extends RuntimeException{

    private final int code;

    public InternalException(int code, String message) {
        super(message);
        this.code = code;
    }

    public InternalException(int code, String message, Throwable e) {
        super(message, e);
        this.code = code;
    }

    public InternalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
