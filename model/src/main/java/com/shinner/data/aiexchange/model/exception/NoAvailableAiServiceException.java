package com.shinner.data.aiexchange.model.exception;

import com.shinner.data.aiexchange.model.enums.ErrorCode;

public class NoAvailableAiServiceException extends BaseException {

    public NoAvailableAiServiceException() {
        super(ErrorCode.NoAvailableAiService.getCode(), ErrorCode.NoAvailableAiService.getMessage());
    }

    public NoAvailableAiServiceException(String msg) {
        super(ErrorCode.NoAvailableAiService.getCode(), msg);
    }

    public NoAvailableAiServiceException(String msg, Throwable e) {
        super(ErrorCode.NoAvailableAiService.getCode(), msg, e);
    }
}
