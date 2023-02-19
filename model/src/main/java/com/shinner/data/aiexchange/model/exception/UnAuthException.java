package com.shinner.data.aiexchange.model.exception;

import com.shinner.data.aiexchange.model.enums.ErrorCode;

public class UnAuthException extends BaseException {

    public UnAuthException() {
        super(ErrorCode.Unauthorized.getCode(), ErrorCode.Unauthorized.getMessage());
    }

    public UnAuthException(String msg) {
        super(ErrorCode.Unauthorized.getCode(), msg);
    }

    public UnAuthException(String msg, Throwable e) {
        super(ErrorCode.Unauthorized.getCode(), msg, e);
    }
}
