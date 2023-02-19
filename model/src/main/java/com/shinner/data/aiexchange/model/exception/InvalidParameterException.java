package com.shinner.data.aiexchange.model.exception;

import static com.shinner.data.aiexchange.model.enums.ErrorCode.InvalidParameterError;

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
