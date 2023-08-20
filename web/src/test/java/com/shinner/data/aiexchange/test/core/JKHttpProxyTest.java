package com.shinner.data.aiexchange.test.core;

import com.shinner.data.aiexchange.common.utils.AESUtil;
import com.shinner.data.aiexchange.common.utils.JsonSerializer;
import com.shinner.data.aiexchange.common.utils.Zlib;
import okhttp3.*;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class JKHttpProxyTest {

    private String key = "121006e472cf485dd0ad62c09f8a16dc";

    @Test
    public void postTest() {
//        String dataStr = "{\"projectId\":\"2022101801\",\"type\":3,\"data\":{\"startTime\":\"1684721325\",\"endTime\":\"1686124566\"}}";
        String dataStr = "{\"projectId\":100,\"type\":2,\"id\":\"101010232\",\"data\":{\"startTime\":1656832200,\"endTime\":1658293900}}";
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
