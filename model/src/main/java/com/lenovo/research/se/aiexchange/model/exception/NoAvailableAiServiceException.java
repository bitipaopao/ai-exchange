package com.lenovo.research.se.aiexchange.model.exception;

import static com.lenovo.research.se.aiexchange.model.enums.ErrorCode.NoAvailableAiService;

public class NoAvailableAiServiceException extends BaseException {

    public NoAvailableAiServiceException() {
        super(NoAvailableAiService.getCode(), NoAvailableAiService.getMessage());
    }

    public NoAvailableAiServiceException(String msg) {
        super(NoAvailableAiService.getCode(), msg);
    }

    public NoAvailableAiServiceException(String msg, Throwable e) {
        super(NoAvailableAiService.getCode(), msg, e);
    }
}
