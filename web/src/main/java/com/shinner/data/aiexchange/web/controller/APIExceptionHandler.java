package com.shinner.data.aiexchange.web.controller;

import com.shinner.data.aiexchange.model.RestResponse;
import com.shinner.data.aiexchange.model.enums.ErrorCode;
import com.shinner.data.aiexchange.model.exception.BaseException;
import com.shinner.data.aiexchange.model.exception.InternalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class APIExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(APIExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public RestResponse handleAPIException(Exception e) {
        Throwable throwable = e.getCause() == null ? e : e.getCause();
        String errorMessage = throwable.getMessage();
        int errorCode = ErrorCode.INTERNAL_ERROR.getCode();
        if (throwable instanceof InternalException) {
            errorCode = ((InternalException) e).getCode();
        } else if (throwable instanceof BaseException) {
            errorCode = ((BaseException) throwable).getCode();
        } else {
            logger.error("handle request error. ", throwable);
        }

        return new RestResponse<>().setCode(errorCode).setMsg(errorMessage);
    }
}