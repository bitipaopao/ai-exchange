package com.lenovo.research.se.aiexchange.test.core;

import com.alibaba.fastjson.JSON;
import com.lenovo.research.se.aiexchange.common.utils.MD5Util;
import com.lenovo.research.se.aiexchange.common.utils.SignUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java.util.*;


class AmapApiTests {
    private final static Logger logger = LoggerFactory.getLogger(AmapApiTests.class);
    private final static String sk = "eeb32de6bc105f901e0826a9d6315a48";

    //summary
    @Test
    void summaryTest() {
        Map<String, String> params = new HashMap<>();
        params.put("key", "a9679858cfde0965ed9868b281cd54bb");
        params.put("apiid", "14");
        params.put("appname", "dz-haimanyun");
        params.put("start_date", "20230124");
        params.put("end_date", "20230124");
        params.put("lab_name", "property_level");
        params.put("type", "visit_flow");
        params.put("level", "level1");
        params.put("wowid", "W000000AXW");
        params.put("time_type", "daily");
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        String token = getMD5Str(strToMD5(params), sk);
        params.put("token", token);
        logger.info(JSON.toJSONString(params));

        String paramStr = SignUtil.mapToStr(params);

        String url = "http://restapi.amap.com/v4/biolap/abapi/query?" + paramStr;
        logger.info("Summary request url: " + url);
        doGet(url);
    }

    // Runtime passenger flow
    @Test
    void runtimePassengerFlowTest() {
        Map<String, String> params = new HashMap<>();
        params.put("key", "a9679858cfde0965ed9868b281cd54bb");
        params.put("apiid", "20");
        params.put("appname", "dz-haimanyun");
        params.put("start_time", "202302031500");
        params.put("end_time", "202302031900");
        params.put("aoiid", "W000000BHT");
        params.put("time_type", "60m");
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        String token = getMD5Str(strToMD5(params), sk);
        params.put("token", token);
        logger.info(JSON.toJSONString(params));
        String paramStr = SignUtil.mapToStr(params);

        String url = "http://restapi.amap.com/v4/biolap/abapi/query?" + paramStr;
        logger.info("Runtime passenger flow request url: " + url);
        doGet(url);
    }

    //Historical passenger flow
    @Test
    void hisPassengerFlowTest() {
        Map<String, String> params = new HashMap<>();
        params.put("key", "a9679858cfde0965ed9868b281cd54bb");
        params.put("apiid", "16");
        params.put("appname", "dz-haimanyun");
        params.put("start_date", "20230201");
        params.put("end_date", "20230203");
        params.put("wowid", "W000000AXW");
        params.put("lab_name", "uv");
        params.put("type", "visit_flow");
        params.put("time_type", "daily");
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        String token = getMD5Str(strToMD5(params), sk);
        params.put("token", token);
        logger.info(JSON.toJSONString(params));
        String paramStr = SignUtil.mapToStr(params);

        String url = "http://restapi.amap.com/v4/biolap/abapi/query?" + paramStr;
        logger.info("Historical passenger flow request url: " + url);
        doGet(url);
    }

    //Thermodynamic diagram
    @Test
    void thermodynamicTest() {
        Map<String, String> params = new HashMap<>();
        params.put("key", "a9679858cfde0965ed9868b281cd54bb");
        params.put("apiid", "24");
        params.put("appname", "dz-haimanyun");
        params.put("aoiid", "W000000BHT");
        params.put("ds", "202302031100");
        params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        String token = getMD5Str(strToMD5(params), sk);
        params.put("token", token);
        logger.info(JSON.toJSONString(params));
        String paramStr = SignUtil.mapToStr(params);

        String url = "http://restapi.amap.com/v4/biolap/abapi/query?" + paramStr;
        logger.info("Thermodynamic diagram request url: " + url);
        doGet(url);
    }

    private void doGet(String url) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        Request request = new Request.Builder()
                .get()
                .url(url)
                .addHeader("Content-Type", "text/plain")
                .build();
        try {
            Response response = client.newCall(request).execute();
            logger.info("response:{}", response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
