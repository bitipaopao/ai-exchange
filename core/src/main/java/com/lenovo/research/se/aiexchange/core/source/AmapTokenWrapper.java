package com.lenovo.research.se.aiexchange.core.source;

import com.lenovo.research.se.aiexchange.common.utils.MD5Util;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Component("amapTokenWrapper")
public class AmapTokenWrapper implements SourceTokenWrapper{
    private final static String sk = "eeb32de6bc105f901e0826a9d6315a48";

    @Override
    public Map<String, String> assembleRequestToken(Map<String, String> params) {
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        String token = getMD5Str(strToMD5(params), sk);
        params.put("token", token);
        return params;
    }

    private static String strToMD5(Map<String, String> params) {
        if (CollectionUtils.isEmpty(params)) {
            return "";
        }
        Optional<String> optionalS = params.keySet().stream()
                .filter(key -> params.get(key) != null)
                .sorted()
                .map(params::get)
                .reduce((a, b) -> a + ";" + b);
        return optionalS.orElse("");
    }

    private String getMD5Str(String str, String sk) {
        str = str + ";" + sk;
        return MD5Util.md5(str, StandardCharsets.UTF_8);
    }
}
