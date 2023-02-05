package com.lenovo.research.se.aiexchange.common.spring.config;

import com.lenovo.research.se.aiexchange.common.servlet.filters.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;

@Configuration
public class CommonFilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> crossFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean
    public FilterRegistrationBean<Filter> requestWrapperFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        RequestWrapperFilter requestWrapperFilter = new RequestWrapperFilter();
        filterRegistrationBean.setFilter(requestWrapperFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("requestWrapperFilter");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter>  requestExpireFilter() {
        FilterRegistrationBean<Filter>  filterRegistrationBean = new FilterRegistrationBean<>();
        RequestExpireFilter requestExpireFilter = new RequestExpireFilter();
        filterRegistrationBean.setFilter(requestExpireFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("requestExpireFilter");
        filterRegistrationBean.setOrder(3);
        return filterRegistrationBean;
    }

}
