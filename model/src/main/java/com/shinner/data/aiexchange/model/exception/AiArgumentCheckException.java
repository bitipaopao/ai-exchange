package com.shinner.data.aiexchange.model.exception;

import com.shinner.data.aiexchange.model.enums.ErrorCode;

public class AiArgumentCheckException extends BaseException {
    public AiArgumentCheckException() {
        super(ErrorCode.AiArgumentCheckFail.getCode(), ErrorCode.AiArgumentCheckFail.getMessage());
    }

    public AiArgumentCheckException(String msg) {
        super(ErrorCode.AiArgumentCheckFail.getCode(), msg);
    }

    public AiArgumentCheckException(String msg, Throwable e) {
        super(ErrorCode.AiArgumentCheckFail.getCode(), msg, e);
    }
}
