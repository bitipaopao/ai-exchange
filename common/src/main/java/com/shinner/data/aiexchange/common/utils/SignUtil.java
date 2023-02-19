package com.shinner.data.aiexchange.common.utils;

import org.apache.commons.codec.net.URLCodec;
import org.springframework.util.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class SignUtil {

    public static String toSignStr(Map<String, Object> params) {
        return mapToStr(params);
    }

    public static String toSignStrWithBody(Map<String, Object> params, Object body) {
        if (Objects.nonNull(body)) {
            params.put("body", body);
        }
        return mapToStr(params);
    }

    public static String mapToStr(Map<?, ?> params) {
        if (CollectionUtils.isEmpty(params)) {
            return "";
        }

        Optional<String> optionalS = params.keySet().stream()
                .filter(key -> params.get(key) != null)
                .sorted()
                .map(key -> key + "=" + SignUtil.objToString(params.get(key)))
                .reduce((a, b) -> a + "&" + b);

        return optionalS.orElse("");
    }

    public static String listToStr(List<?> list) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        return list.stream()
                .filter(Objects::nonNull)
                .map(SignUtil::objToUTF8Str)
                .collect(Collectors.joining(","));
    }

    public static String arrayToStr(Object[] objArray) {
        if (objArray == null || objArray.length == 0) {
            return "";
        }
        return Arrays.stream(objArray)
                .map(SignUtil::objToUTF8Str)
                .collect(Collectors.joining(","));
    }

    private static String objToString(Object obj) {
        if (Objects.isNull(obj)) {
            return "";
        }

        if (obj instanceof Map) {
            return "{" + mapToStr((Map<?, ?>) obj) + "}";
        }

        if (obj instanceof Collection) {
            return listToStr((List<?>) obj) ;
        }

        if (obj.getClass().isArray()) {
            return arrayToStr((Object[]) obj);
        }

        if (obj instanceof Integer | obj instanceof String
                | obj instanceof Character | obj instanceof Float
                | obj instanceof Boolean | obj instanceof BigDecimal
                | obj instanceof Double | obj instanceof Long) {
            return obj.toString();
        }

        throw new RuntimeException("Un support the clazz to url str." + obj.getClass());
    }

    public static String objToUTF8Str(Object obj) {
        String result = objToString(obj);
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
