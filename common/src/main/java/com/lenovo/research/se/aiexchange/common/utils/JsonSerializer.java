package com.lenovo.research.se.aiexchange.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

@SuppressWarnings("unused")
public class JsonSerializer {

    public static Gson gson = new Gson();

    public static <T> T deserialize(String jsonString, Class<T> classType) {
        return gson.fromJson(jsonString, classType);
    }

    public static Object deserialize(String jsonString, Type type) {
        if (type.getTypeName().equals("java.lang.String")) {
            return jsonString;
        }
        return gson.fromJson(jsonString, type);
    }

    public static String serialize(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        }

        return gson.toJson(obj);
    }

    public static String serialize(Object obj, String defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        return serialize(obj);
    }

    public static <T> T deserialize(String jsonString, TypeToken<T> typeToken) {
        return gson.fromJson(jsonString, typeToken.getType());
    }
}
