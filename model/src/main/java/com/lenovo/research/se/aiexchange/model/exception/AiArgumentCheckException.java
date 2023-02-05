package com.lenovo.research.se.aiexchange.model.exception;

import static com.lenovo.research.se.aiexchange.model.enums.ErrorCode.AiArgumentCheckFail;

public class AiArgumentCheckException extends BaseException {
    public AiArgumentCheckException() {
        super(AiArgumentCheckFail.getCode(), AiArgumentCheckFail.getMessage());
    }

    public AiArgumentCheckException(String msg) {
        super(AiArgumentCheckFail.getCode(), msg);
    }

    public AiArgumentCheckException(String msg, Throwable e) {
        super(AiArgumentCheckFail.getCode(), msg, e);
    }
}
