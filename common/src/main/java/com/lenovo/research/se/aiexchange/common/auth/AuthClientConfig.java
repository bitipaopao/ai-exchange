package com.lenovo.research.se.aiexchange.common.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "mw.auth.client", name = "enable", havingValue = "true")
public class AuthClientConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/**");
        ClientAuthInterceptor clientAuthInterceptor = new ClientAuthInterceptor();
        registry.addInterceptor(clientAuthInterceptor)
                .addPathPatterns(urlPatterns);
    }
}
