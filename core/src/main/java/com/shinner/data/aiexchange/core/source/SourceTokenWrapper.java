package com.shinner.data.aiexchange.core.source;

import com.shinner.data.aiexchange.model.RestResponse;

import java.util.Map;

public interface SourceTokenWrapper {
    String suffix = "TokenWrapper";

    default Map<String, Object> assembleRequestToken(Map<String, Object> params) {
        return params;
    }

    default String assembleRequestBody(String body) {
        return body;
    }

    default RestResponse assembleResult(String responseStr) {
        throw new RuntimeException("No wrapper to parse respones. With responseStr: " + responseStr);
    }
}
