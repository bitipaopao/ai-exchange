package com.lenovo.research.se.aiexchange.common.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Conditional(ConditionalOnPropertyNotEmpty.OnPropertyNotEmptyCondition.class)
public @interface ConditionalOnPropertyNotEmpty {
    String value();

    class OnPropertyNotEmptyCondition implements Condition {
        private final static Logger logger = LoggerFactory.getLogger(OnPropertyNotEmptyCondition.class);
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> attrs = metadata.getAnnotationAttributes(ConditionalOnPropertyNotEmpty.class.getName());
            String propertyName = (String) attrs.get("value");
            boolean result = context.getEnvironment().containsProperty(propertyName);
            logger.info("ConditionalOnProperty Not Empty check propertyName:" +  propertyName + " val:" + result);
            return result;
        }
    }
}
