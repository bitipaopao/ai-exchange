package com.lenovo.research.se.aiexchange.model.exception;

import static com.lenovo.research.se.aiexchange.model.enums.ErrorCode.InvalidParameterError;

public class InvalidParameterException extends BaseException {

    public InvalidParameterException() {
        super(InvalidParameterError.getCode(), InvalidParameterError.getMessage());
    }

    public InvalidParameterException(String msg) {
        super(InvalidParameterError.getCode(), msg);
    }

    public InvalidParameterException(String msg, Throwable e) {
        super(InvalidParameterError.getCode(), msg, e);
    }
}
