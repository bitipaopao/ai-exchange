package com.shinner.data.aiexchange.core.source;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component("defaultTokenWrapper")
public class DefaultTokenWrapper implements SourceTokenWrapper{

    @Override
    public Map<String, Object> assembleRequestToken(Map<String, Object> params) {
        return params;
    }
}
