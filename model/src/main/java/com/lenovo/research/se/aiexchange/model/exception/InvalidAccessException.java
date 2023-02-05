package com.lenovo.research.se.aiexchange.model.exception;

import static com.lenovo.research.se.aiexchange.model.enums.ErrorCode.InvalidAccessKey;

public class InvalidAccessException extends BaseException {

    public InvalidAccessException() {
        super(InvalidAccessKey.getCode(), InvalidAccessKey.getMessage());
    }

    public InvalidAccessException(String msg) {
        super(InvalidAccessKey.getCode(), msg);
    }

    public InvalidAccessException(String msg, Throwable e) {
        super(InvalidAccessKey.getCode(), msg, e);
    }
}
