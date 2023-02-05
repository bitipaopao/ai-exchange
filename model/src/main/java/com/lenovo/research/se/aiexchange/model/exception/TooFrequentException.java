package com.lenovo.research.se.aiexchange.model.exception;

import static com.lenovo.research.se.aiexchange.model.enums.ErrorCode.TooFrequentRequests;

public class TooFrequentException extends BaseException {

    public TooFrequentException() {
        super(TooFrequentRequests.getCode(), TooFrequentRequests.getMessage());
    }

    public TooFrequentException(String msg) {
        super(TooFrequentRequests.getCode(), msg);
    }

    public TooFrequentException(String msg, Throwable e) {
        super(TooFrequentRequests.getCode(), msg, e);
    }
}
