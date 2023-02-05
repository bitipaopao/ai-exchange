package com.lenovo.research.se.aiexchange.test.core;

import okhttp3.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpProxyTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void httpProxyTest() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\"subjectId\":2,\"questiontext\":\"圆锥的底面半径是5cm，侧面展开图的圆心角是180<sup>0</sup> ，圆锥的高是（&nbsp;&nbsp; ）   \",\"phaseId\":1,\"questions\":[],\"questionIdx\":0,\"questiontype\":2,\"source\":1}");
        Request request = new Request.Builder()
                .url("http://10.121.120.229:8081/kpannotate")
                .method("POST", body)
                .addHeader("Content-Type", "text/plain")
                .build();
        try {
            Response response = client.newCall(request).execute();
            logger.info("response:{}", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void limitTest() {
        for (int i = 0; i < 10; i++) {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "{\"subjectId\":2,\"questiontext\":\"圆锥的底面半径是5cm，侧面展开图的圆心角是180<sup>0</sup> ，圆锥的高是（&nbsp;&nbsp; ）   \",\"phaseId\":1,\"questions\":[],\"questionIdx\":0,\"questiontype\":2,\"source\":1}");
            Request request = new Request.Builder()
                    .url("http://localhost:8081/api/v1/request/kp_label/kp_annotate?argMd5=1cee0ad3aac1b8b2f270841d2fbb0261")
                    .method("POST", body)
                    .addHeader("Content-Type", "text/plain")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                logger.info("response:{}", response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
