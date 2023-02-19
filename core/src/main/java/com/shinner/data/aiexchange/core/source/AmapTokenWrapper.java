package com.shinner.data.aiexchange.core.source;

import com.shinner.data.aiexchange.common.utils.MD5Util;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Component("amapTokenWrapper")
public class AmapTokenWrapper implements SourceTokenWrapper{
    private final static String sk = "eeb32de6bc105f901e0826a9d6315a48";

    @Override
    public Map<String, Object> assembleRequestToken(Map<String, Object> params) {
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        String token = getMD5Str(strToMD5(params), sk);
        params.put("token", token);
        return params;
    }

    private static String strToMD5(Map<String, Object> params) {
        if (CollectionUtils.isEmpty(params)) {
            return "";
        }
        Optional<String> optionalS = params.keySet().stream()
                .filter(key -> params.get(key) != null)
                .sorted()
                .map(key -> params.get(key).toString())
                .reduce((a, b) -> a + ";" + b);
        return optionalS.orElse("");
    }

    private String getMD5Str(String str, String sk) {
        str = str + ";" + sk;
        return MD5Util.md5(str, StandardCharsets.UTF_8);
    }
}
