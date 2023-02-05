package com.lenovo.research.se.aiexchange.model.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public enum Valid {
	MANUAL_DEL(-1), AUTO_DEL(0),ACTIVATE(1),;

    private final int id;

    Valid(int id){
       this.id =id;
    }

    public static Map<Integer, Valid> valueMap;

    static {
        Map<Integer, Valid> map = new HashMap<>();
        for (Valid valid : Valid.values()) {
            map.put(valid.getId(), valid);
        }
        valueMap = Collections.unmodifiableMap(map);
    }

    public static Valid valueOf(int id){
       return valueMap.get(id);
    }

    public int getId() {
       return id;
    }
}
