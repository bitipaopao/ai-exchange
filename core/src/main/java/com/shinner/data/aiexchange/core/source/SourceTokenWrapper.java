package com.shinner.data.aiexchange.core.source;

import java.util.Map;

public interface SourceTokenWrapper {
    String suffix = "TokenWrapper";
    Map<String,Object> assembleRequestToken(Map<String,Object> params);
}
