package com.lenovo.research.se.aiexchange.common.utils;

import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class PojoUtil {

    public static Map<String, Object> pojo2Map(Object obj) {
        Assert.notNull(obj, "obj should not be null which want to map");
        String jsonStr = JsonSerializer.serialize(obj);
        return JsonSerializer.deserialize(jsonStr, new TypeToken<Map<String, Object>>(){});
    }

    public static Map<String, Object> jsonStrToMap(String jsonStr) {
        return JsonSerializer.deserialize(jsonStr, new TypeToken<Map<String, Object>>(){});
    }

    public static  <T> T mapToPojo(Map<String, Object> map, Class<T> tClass) {
        String mapStr = JsonSerializer.serialize(map);
        return JsonSerializer.deserialize(mapStr, tClass);
    }

    public static  <T> T pojoToPojo(Object obj, Class<T> tClass) {
        Map<String, Object> tmpMap = pojo2Map(obj);
        return mapToPojo(tmpMap, tClass);
    }

    public static String mapToUrlParams(Map<?, ?> params) {
        if (CollectionUtils.isEmpty(params)) {
            return "";
        }

        Optional<String> optionalS = params.entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .map(entry -> entry.getKey() + "=" + toUrlParamStrEncode(entry.getValue()))
                .reduce((a, b) -> a + "&" + b);

        return optionalS.orElse("");
    }

    public static String listToString(List<?> list) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        return list.stream()
                .filter(Objects::nonNull)
                .map(PojoUtil::toUrlParamStrEncode)
                .collect(Collectors.joining(","));
    }

    public static String arrayToUrlParams(Object[] objArray) {
        if (objArray == null || objArray.length == 0) {
            return "";
        }
        return Arrays.stream(objArray)
                .map(PojoUtil::toUrlParamStrEncode)
                .collect(Collectors.joining(","));
    }

    public static String toUrlParamStr(Object obj) {
        if (Objects.isNull(obj)) {
            return "";
        }

        if (obj instanceof Map) {
            return "{" + mapToUrlParams((Map<?, ?>) obj) + "}";
        }

        if (obj instanceof Collection) {
            return listToString((List<?>) obj);
        }

        if (obj.getClass().isArray()) {
            return arrayToUrlParams((Object[]) obj);
        }

        if (obj instanceof Integer | obj instanceof String
                | obj instanceof Character | obj instanceof Float
                | obj instanceof Boolean | obj instanceof BigDecimal
                | obj instanceof Double | obj instanceof Long) {
            return obj.toString();
        }

        throw new RuntimeException("Un support the clazz to url str." + obj.getClass());
    }

    public static String toUrlParamStrEncode(Object obj) {
        String result = toUrlParamStr(obj);
        try {
            if (!StringUtil.isEmpty(result)) {
                return new URLCodec()
                        .encode(result, StandardCharsets.UTF_8.toString());
            }
        } catch (UnsupportedEncodingException e) {
            // ignore
        }
        return result;
    }
}