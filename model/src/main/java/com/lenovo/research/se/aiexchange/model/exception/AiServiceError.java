package com.lenovo.research.se.aiexchange.model.exception;

import static com.lenovo.research.se.aiexchange.model.enums.ErrorCode.AiServerError;

public class AiServiceError extends BaseException {

    public AiServiceError() {
        super(AiServerError.getCode(), AiServerError.getMessage());
    }

    public AiServiceError(String msg) {
        super(AiServerError.getCode(), msg);
    }

    public AiServiceError(String msg, Throwable e) {
        super(AiServerError.getCode(), msg, e);
    }
}
