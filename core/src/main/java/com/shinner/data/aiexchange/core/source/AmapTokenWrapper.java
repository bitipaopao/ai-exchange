package com.shinner.data.aiexchange.core.source;

import com.alibaba.fastjson.JSONObject;
import com.shinner.data.aiexchange.common.utils.JsonSerializer;
import com.shinner.data.aiexchange.common.utils.MD5Util;
import com.shinner.data.aiexchange.model.RestResponse;
import com.shinner.data.aiexchange.model.enums.ErrorCode;
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

    @Override
    public RestResponse assembleResult(String responseStr) {
        JSONObject jsonResult = JSONObject.parseObject(responseStr);
        int errorCode = (int) jsonResult.get("errcode");
        if (errorCode == 0) {
            RestResponse restResponse = JsonSerializer.deserialize(responseStr, RestResponse.class);
            return restResponse.setCode(ErrorCode.Success.getCode());
        } else {
            return new RestResponse().setMsg((String) jsonResult.get("errmsg")).setCode(ErrorCode.INTERNAL_ERROR.getCode());
        }
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
