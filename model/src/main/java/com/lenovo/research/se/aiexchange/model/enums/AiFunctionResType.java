package com.lenovo.research.se.aiexchange.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum AiFunctionResType {

    Synchronous(0),
    Asynchronous(1),
    ;

    static {
        Map<Integer, AiFunctionResType> map = Arrays.stream(AiFunctionResType.values())
                .collect(Collectors.toMap(AiFunctionResType::getCode, Function.identity()));
        codeMaps = Collections.unmodifiableMap(map);
    }

    private final static Map<Integer, AiFunctionResType> codeMaps;
    private final int code;

    AiFunctionResType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static AiFunctionResType valueOf(int code) {
        if (!codeMaps.containsKey(code)) {
            throw new RuntimeException("Unsupport AiFunctionBathc for code:" + code);
        }
        return codeMaps.get(code);
    }
}
