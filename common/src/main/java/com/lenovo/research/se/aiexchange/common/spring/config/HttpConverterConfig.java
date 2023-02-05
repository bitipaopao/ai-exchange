package com.lenovo.research.se.aiexchange.common.spring.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "mw.inward", name = "fastjson-converter", havingValue = "true")
public class HttpConverterConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                StandardCharsets.UTF_8);
        converters.add(converter);
        converters.add(gsonHttpMessageConverter());
    }

    private GsonHttpMessageConverter gsonHttpMessageConverter() {
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("U");
        builder.excludeFieldsWithModifiers(Modifier.PROTECTED);
        Gson gson = builder.create();
        gsonHttpMessageConverter.setGson(gson);
        return gsonHttpMessageConverter;
    }

}
