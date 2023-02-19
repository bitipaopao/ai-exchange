package com.shinner.data.aiexchange.model.exception;

import com.shinner.data.aiexchange.model.enums.ErrorCode;

public class InvalidAccessException extends BaseException {

    public InvalidAccessException() {
        super(ErrorCode.InvalidAccessKey.getCode(), ErrorCode.InvalidAccessKey.getMessage());
    }

    public InvalidAccessException(String msg) {
        super(ErrorCode.InvalidAccessKey.getCode(), msg);
    }

    public InvalidAccessException(String msg, Throwable e) {
        super(ErrorCode.InvalidAccessKey.getCode(), msg, e);
    }
}
