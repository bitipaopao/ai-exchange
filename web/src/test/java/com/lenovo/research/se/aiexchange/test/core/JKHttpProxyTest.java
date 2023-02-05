package com.lenovo.research.se.aiexchange.test.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lenovo.research.se.aiexchange.common.utils.AESUtil;
import com.lenovo.research.se.aiexchange.common.utils.JsonSerializer;
import com.lenovo.research.se.aiexchange.common.utils.Zlib;
import okhttp3.*;
import org.junit.Test;
import springfox.documentation.spring.web.json.Json;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

public class JKHttpProxyTest {

    private String key = "121006e472cf485dd0ad62c09f8a16dc";

    @Test
    public void postTest() {
        String dataStr = "{\"projectId\": 2022052001, \"type\": 3, \"data\": {\"startTime\": 1664526444, \"endTime\": 1667118444}}";
        byte[] dataEncode = Zlib.compress(dataStr.getBytes());
        String requestBody = AESUtil.encryptCBC(key, dataEncode);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestBody);
        Request request = new Request.Builder()
                .url("https://open.isjike.com/api/tourism/flow/history/arrive/bjmx")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseStr = response.body().string();
            System.out.println("responseStr:" + responseStr);
            Map jsonObject = (Map)JsonSerializer.deserialize(responseStr, Map.class);
            String resultStr = AESUtil.decryptCBC(key, jsonObject.get("data").toString());
            System.out.println(resultStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
