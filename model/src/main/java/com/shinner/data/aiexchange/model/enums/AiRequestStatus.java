package com.shinner.data.aiexchange.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum AiRequestStatus {

    Undo(0),
    Doing(1),
    Done(2),
    NeedCallback(3),
    CallbackSuccess(4),
    CallbackError(5),
    Error(99),
    ;

    static {
        Map<Integer, AiRequestStatus> map = Arrays.stream(AiRequestStatus.values())
                .collect(Collectors.toMap(AiRequestStatus::getCode, Function.identity()));
        codeMaps = Collections.unmodifiableMap(map);
    }

    private final static Map<Integer, AiRequestStatus> codeMaps;
    private final int code;

    AiRequestStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static AiRequestStatus valueOf(int code) {
        if (!codeMaps.containsKey(code)) {
            throw new RuntimeException("Unsupport AiFunctionBathc for code:" + code);
        }
        return codeMaps.get(code);
    }
}
