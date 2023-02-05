package com.lenovo.research.se.aiexchange.core.manager;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "ai.http")
public class HttpAiServiceProperties {

    private Map<String, List<String>> services;

    public Map<String, List<String>> getServices() {
        return services;
    }

    public HttpAiServiceProperties setServices(Map<String, List<String>> services) {
        this.services = services;
        return this;
    }
}
