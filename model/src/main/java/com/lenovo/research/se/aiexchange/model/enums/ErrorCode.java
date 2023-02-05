package com.lenovo.research.se.aiexchange.model.enums;

@SuppressWarnings("unused")
public enum ErrorCode {
    OldSuccess(0, "success"),
    Success(1000, "success"),
    INTERNAL_ERROR(500, "Internal Server Error"),
    InvalidParameterError(400, "Bad Request parameter"),
    Unauthorized(401, "Unauthorized"),
    InvalidAccessKey(402, "Invalid access key"),
    Forbidden(403, "Forbidden"),
    NotFound(404, "Not Found"),
    NoAvailableAiService(422, "No Available Ai Service"),
    AiArgumentCheckFail(423,"Ai Argument check Fail"),
    TooFrequentRequests(429, "Requests are too frequent"),
    AiServerError(503, "Service Unavailable"),
    ;
    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Boolean isSuccess() {
        return this == Success;
    }
}
