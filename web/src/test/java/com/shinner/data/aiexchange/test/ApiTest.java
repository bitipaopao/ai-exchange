package com.shinner.data.aiexchange.test;

import com.shinner.data.aiexchange.signature.MD5;
import com.shinner.data.aiexchange.signature.SignatureHelper;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ApiTest {
    private final static Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void composeRequestTest() {
        String argument = "";
        String fileName = "body.txt";
        URL url = ApiTest.class.getClassLoader().getResource(fileName);
        if (Objects.isNull(url)) {
            throw new NoSuchElementException();
        }

        try {
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            argument = IOUtils.toString(inputStream, StandardCharsets.UTF_8.displayName());

        } catch (Exception e) {
            logger.error("Upload test error.", e);
        }
        String sk = "k4AcyAXNVHyzjukG4uukhhLYYtn1rNjj";
        String argMd5 = MD5.md5(argument, "UTF-8");
        String urlParaStr = "access-key=rcaksWuBQn12027gCTiXxh&argMd5=" + argMd5 + "&salt=0.234153348";
        String pramStr = SignatureHelper.generatorSignUrl(urlParaStr, sk);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, argument);
        Request request = new Request.Builder()
                .url("http://10.121.120.229:8080/smartedu-ai/api/v1/request/compose/q_annotate?" + pramStr)
                .method("POST", body)
                .addHeader("Content-Type", "text/plain")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
