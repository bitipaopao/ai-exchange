package com.shinner.data.aiexchange.model.exception;

import com.shinner.data.aiexchange.model.enums.ErrorCode;

public class TooFrequentException extends BaseException {

    public TooFrequentException() {
        super(ErrorCode.TooFrequentRequests.getCode(), ErrorCode.TooFrequentRequests.getMessage());
    }

    public TooFrequentException(String msg) {
        super(ErrorCode.TooFrequentRequests.getCode(), msg);
    }

    public TooFrequentException(String msg, Throwable e) {
        super(ErrorCode.TooFrequentRequests.getCode(), msg, e);
    }
}
