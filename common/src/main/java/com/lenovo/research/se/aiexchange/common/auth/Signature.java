package com.lenovo.research.se.aiexchange.common.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Signature {
    Logger logger = LoggerFactory.getLogger(Signature.class);

    default void checkTimestamp(Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }

    default void sign(String accessKey, Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }

    default void sign(String accessKey, Object body, Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }

    default void verify(Map<String, Object> params) {
        throw new UnsupportedOperationException();
    }

    default void verify(Map<String, Object> params, Object body) {
        throw new UnsupportedOperationException();
    }

    default void verify(HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }

    default Map<String, Object> getRequestParameterMap(HttpServletRequest request) {
        Set<String> parameterNames = request.getParameterMap().keySet();
        Map<String, Object> parameterMap = CollectionUtils.isEmpty(parameterNames)
                ? new HashMap<>()
                : parameterNames.stream()
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                request::getParameter
                        )
                );

//        int contentLength = request.getContentLength();
//        logger.info("content length:" + contentLength);
//        if (contentLength > 0) {
//            StringBuilder sb = new StringBuilder();
//            try (BufferedReader br = request.getReader()) {
//                String str;
//                while ((str = br.readLine()) != null) {
//                    sb.append(str);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("Read requst body error", e);
//            }
//
//            /* Compatible with the old version. Do not sign the "body" data for v2.23.0.1.****
//            String body = sb.toString();
//            if (!StringUtils.isEmpty(body)) {
//                if ("application/json".equals(request.getContentType())) {
//                    parameterMap.put("body", PojoUtil.jsonStrToMap(body));
//                }
//                parameterMap.put("body", body);
//            }*/
//        }

        return parameterMap;
    }

}
