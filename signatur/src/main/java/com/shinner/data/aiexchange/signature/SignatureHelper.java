package com.shinner.data.aiexchange.signature;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

public class SignatureHelper {

    public static String generatorSignUrl(String paramStr, String secret) {
        String[] params = paramStr.split("&");
        long now = System.currentTimeMillis()/1000;
        Map<String, String> parameters = Arrays.stream(params)
                .map(
                        str -> str.split("=")
                ).collect(Collectors.toMap(sa ->  sa[0],sa ->  sa[1]));
        parameters.put("timestamp",  String.valueOf(now));
        String sign = URLEncoder.encode(generator(parameters, secret));
        parameters.put("sign", sign);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : parameters.keySet()) {
            if (parameters.containsKey(key))
                stringBuilder.append(key).append("=").append(parameters.get(key)).append("&");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
       return stringBuilder.toString();
    }


    public static String generator(Map<String, String> params, String secret) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        Object value;
        for (String key : keys) {
            value = params.get(key);
            if (value != null)
                stringBuilder.append(key).append("=").append(value).append("&");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        String toMd5 = stringBuilder.toString();
        String md5val = MD5.md5(toMd5, "UTF-8");
        return hashHmac(md5val, secret).toUpperCase();
    }

    private static String hashHmac(String text, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return Base64.getEncoder()
                    .encodeToString(sha256_HMAC.doFinal(text.getBytes()));
        } catch (Exception e) {
            // ignore
        }
        return "";
    }
}
