package com.shinner.data.aiexchange.common.servlet.filters;

import com.shinner.data.aiexchange.common.utils.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
public class RequestExpireFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        logger.info("====================Request Start========================");
        logger.info("handle request start. path=" + ((HttpServletRequest) request).getServletPath()
                + "parameters=" + JsonSerializer.serialize(request.getParameterMap()));
        long startTime = System.currentTimeMillis();
        try {
            chain.doFilter(request, response);
        } catch (Throwable e) {
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            String errorMessage = throwable.getMessage();

            logger.error("handle request error. path=" + ((HttpServletRequest) request).getServletPath()
                    + "parameters=" + JsonSerializer.serialize(request.getParameterMap()), throwable);
        } finally {
            logger.info("Process request expire time: {}ms", (System.currentTimeMillis() - startTime));
            logger.info("====================Request   End========================");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("Unified exception handler initialization...");
    }

    @Override
    public void destroy() {

    }
}
