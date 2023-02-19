package com.shinner.data.aiexchange.common.servlet.filters;

import com.shinner.data.aiexchange.common.servlet.RequestParameterWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestWrapperFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(RequestWrapperFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
             request = new RequestParameterWrapper(request);
             filterChain.doFilter(request, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
