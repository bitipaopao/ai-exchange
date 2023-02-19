package com.shinner.data.aiexchange.model.exception;

import com.shinner.data.aiexchange.model.enums.ErrorCode;

public class AiServiceError extends BaseException {

    public AiServiceError() {
        super(ErrorCode.AiServerError.getCode(), ErrorCode.AiServerError.getMessage());
    }

    public AiServiceError(String msg) {
        super(ErrorCode.AiServerError.getCode(), msg);
    }

    public AiServiceError(String msg, Throwable e) {
        super(ErrorCode.AiServerError.getCode(), msg, e);
    }
}
