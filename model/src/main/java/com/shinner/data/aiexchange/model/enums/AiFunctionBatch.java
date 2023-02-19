package com.shinner.data.aiexchange.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum  AiFunctionBatch {
    Concurrent(0),
    Serial(1),
    ;

    static {
        Map<Integer, AiFunctionBatch> map = Arrays.stream(AiFunctionBatch.values())
                .collect(Collectors.toMap(AiFunctionBatch::getCode, Function.identity()));
        codeMaps = Collections.unmodifiableMap(map);
    }

    private final static Map<Integer, AiFunctionBatch> codeMaps;
    private final int code;

    AiFunctionBatch(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static AiFunctionBatch valueOf(int code) {
        if (!codeMaps.containsKey(code)) {
            throw new RuntimeException("Unsupport AiFunctionBathc for code:" + code);
        }
        return codeMaps.get(code);
    }
}
