package com.lenovo.research.se.aiexchange.core.source;

import java.util.Map;

public interface SourceTokenWrapper {
    String suffix = "TokenWrapper";
    Map<String,Object> assembleRequestToken(Map<String,Object> params);
}
