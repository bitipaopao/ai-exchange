package com.lenovo.research.se.aiexchange.model.exception;

import static com.lenovo.research.se.aiexchange.model.enums.ErrorCode.Unauthorized;

public class UnAuthException extends BaseException {

    public UnAuthException() {
        super(Unauthorized.getCode(), Unauthorized.getMessage());
    }

    public UnAuthException(String msg) {
        super(Unauthorized.getCode(), msg);
    }

    public UnAuthException(String msg, Throwable e) {
        super(Unauthorized.getCode(), msg, e);
    }
}
