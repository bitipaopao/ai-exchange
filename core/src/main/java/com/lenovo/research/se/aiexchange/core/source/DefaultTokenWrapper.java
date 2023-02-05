package com.lenovo.research.se.aiexchange.core.source;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component("defaultTokenWrapper")
public class DefaultTokenWrapper implements SourceTokenWrapper{

    @Override
    public Map<String, String> assembleRequestToken(Map<String, String> params) {
        return params;
    }
}
