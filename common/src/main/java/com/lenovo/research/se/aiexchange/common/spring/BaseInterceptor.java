package com.lenovo.research.se.aiexchange.common.spring;

import com.lenovo.research.se.aiexchange.common.utils.StringUtil;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("unused")
public abstract class BaseInterceptor implements HandlerInterceptor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseInterceptor.class);
    protected static final String CONTENT_TYPE = "Content-type";
    protected static final String AUTHENTICATION = "Authentication";
    protected static final String TOKEN = "token";
    protected static final String REAL_M = "Realm";
    protected static final String ACAO = "Access-Control-Allow-Origin";
    protected static final String USER_AGENT = "User-Agent";
    protected static final String RAND_CODE = "rndKey";

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {
        String path = request.getServletPath();
        String realm = request.getHeader(REAL_M);
        String contentType = request.getHeader(CONTENT_TYPE);
        String authentication = request.getHeader(AUTHENTICATION);
        String useragent = StringEscapeUtils.escapeHtml4(request.getHeader(USER_AGENT));
        String rndCode = StringEscapeUtils.escapeHtml4(request.getHeader(RAND_CODE));

        if (StringUtil.isEmpty(authentication)) {
            authentication = request.getHeader(TOKEN);
        }

        dealWithHeader(request, useragent, rndCode);

        LOGGER.info("preHandle() ==> path -> (" + path + ")");
        LOGGER.info("preHandle() ==> realm -> (" + realm + "), content-type -> (" + contentType + "), authentication -> (" + authentication + ")");
        if (request.getMethod().toLowerCase().equals("options")) {
            LOGGER.info("preHandle() ==> called options");
            return true;
        }

        if (request.getDispatcherType() == DispatcherType.ASYNC) {
            return true;
        }

        if (path.startsWith("/healthz")
                || path.startsWith("/actuator")
                || path.startsWith("/metrics")
                || path.startsWith("/webjars")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/swagger-ui.html")
                || path.startsWith("/v2")) {
            return true;
        }
        return handleInterceptor(request, response, path, realm, authentication, contentType);
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request,
                           @NonNull HttpServletResponse response,
                           @NonNull Object handler,
                           ModelAndView modelAndView) {
    }

    protected void dealWithHeader(HttpServletRequest request, String userAgent, String rndCode) {

    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception exception) {
    }

    protected abstract boolean handleInterceptor(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 String path,
                                                 String realm,
                                                 String encryptedToken,
                                                 String contentType);

}
