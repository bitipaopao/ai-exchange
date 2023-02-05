package com.lenovo.research.se.aiexchange.common.auth;

import com.lenovo.research.se.aiexchange.common.spring.SpringBootUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class ClientAuthInterceptor
		implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ClientAuthInterceptor.class);

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        ClientAuth clientAuth = ((HandlerMethod) handler).getMethodAnnotation(ClientAuth.class);
        if (Objects.isNull(clientAuth)) {
            clientAuth = ((HandlerMethod) handler).getBean().getClass().getAnnotation(ClientAuth.class);
        }

        if (Objects.isNull(clientAuth)) {
            return true;
        }

        Signature signature = (Signature) SpringBootUtil.getBean(clientAuth.signWay() + "Signature");
        signature.verify(request);
        return true;
    }
}
