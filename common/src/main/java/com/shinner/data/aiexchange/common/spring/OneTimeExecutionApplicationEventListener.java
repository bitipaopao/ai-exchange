package com.shinner.data.aiexchange.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.lang.NonNull;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class OneTimeExecutionApplicationEventListener implements ApplicationListener<ApplicationEvent> , ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public final void onApplicationEvent(@NonNull ApplicationEvent applicationEvent) {
        if (isOriginalEventSource(applicationEvent) && applicationEvent instanceof ApplicationContextEvent) {
            onApplicationContextEvent((ApplicationContextEvent) applicationEvent);
        }
    }

    protected abstract void onApplicationContextEvent(ApplicationContextEvent event);

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    private boolean isOriginalEventSource(ApplicationEvent event) {
        return (applicationContext == null) || Objects.equals(applicationContext, event.getSource());
    }
}
