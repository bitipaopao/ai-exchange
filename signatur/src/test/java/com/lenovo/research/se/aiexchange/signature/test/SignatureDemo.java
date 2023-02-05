package com.lenovo.research.se.aiexchange.signature.test;

import com.lenovo.research.se.aiexchange.signature.SignatureHelper;
import okhttp3.*;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class SignatureDemo {

    public static void main(String[] args) throws InterruptedException {
        String urlParaStr = "salt=0.234153348&argMd5=5faec0279e190127bc2204205cacfa5e&access-key=rcaksWuBQn12027gCTiXxh";
        // sk，和ak(access-key)匹配的密钥字符串，后续文档提供
        String sk = "k4AcyAXNVHyzjukG4uukhhLYYtn1rNjj";
        String newUrlParaStr = sign(urlParaStr, sk);
        System.out.println(newUrlParaStr);
//        for (int i = 0; i < 10; i++) {
//            // url中“？”后参数字符串，包含需鉴权字符串“access-key”和“salt”，时间戳sign方法会自动添加
//            String urlParaStr = "access-key=rcaksWuBQn12027gCTiXxh&argMd5=b729417e1345f49b451464647e39d371&salt=0.234153348";
//            // sk，和ak(access-key)匹配的密钥字符串，后续文档提供
//            String sk = "k4AcyAXNVHyzjukG4uukhhLYYtn1rNjj";
//            String newUrlParaStr = sign(urlParaStr, sk);
//            System.out.println(newUrlParaStr);
//            requestTests(newUrlParaStr);
//            Thread.sleep(5000);
//        }

    }

    public static String sign(String urlParaStr, String sk) {
        return SignatureHelper.generatorSignUrl(urlParaStr, sk);
    }

    public static void requestTests(String urlStr) {
        String urlPath = "http://10.121.120.135:8080/smartedu-ai/api/v1/request/ppt_qg/qg?";

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\"ppt_path\":\"learning/ppt/计算机维修基础2022.pptx\",\"ppt_name\":\"计算机维修基础2022.pptx\",\"kegang_path\":\"learning/kegang/计算机维修基础2022.xls\",\"kegang_name\":\"计算机维修基础2022.xls\"}");
        Request request = new Request.Builder()
                .url(urlPath + urlStr)
                .method("POST", body)
                .addHeader("Content-Type", "text/plain")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response);
        } catch (IOException e) {
            assert (e instanceof SocketTimeoutException);
        }
    }


}
