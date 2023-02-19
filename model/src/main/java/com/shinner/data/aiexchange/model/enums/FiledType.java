package com.shinner.data.aiexchange.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum FiledType {
    Obj(0),
    Arr(1),
    ;

    static {
        Map<Integer, FiledType> map = Arrays.stream(FiledType.values())
                .collect(Collectors.toMap(FiledType::getCode, Function.identity()));
        codeMaps = Collections.unmodifiableMap(map);
    }

    private final static Map<Integer, FiledType> codeMaps;
    private final int code;

    FiledType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static FiledType valueOf(int code) {
        if (!codeMaps.containsKey(code)) {
            throw new RuntimeException("Unsupport AiFunctionBathc for code:" + code);
        }
        return codeMaps.get(code);
    }
}
