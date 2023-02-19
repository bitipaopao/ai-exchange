package com.shinner.data.aiexchange.common.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
@Component
public class SpringBootUtil implements ApplicationContextAware {
    private final static Logger logger = LoggerFactory.getLogger(SpringBootUtil.class);

    private static ApplicationContext applicationContext;

    private SpringBootUtil() {

    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        if (SpringBootUtil.applicationContext == null) {
            SpringBootUtil.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static boolean registerInfrastructureBean(BeanDefinitionRegistry beanDefinitionRegistry, String beanName, Class<?> beanType) {
        if (beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            return false;
        }

        RootBeanDefinition beanDefinition = new RootBeanDefinition(beanType);
        beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        beanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition);
        logger.info("The Infrastructure bean definition [" + beanDefinition + "with name [" + beanName + "] has been registered.");
        return true;
    }

    public static int registerSpringFactoriesBeans(BeanDefinitionRegistry registry, Class<?>... factoryClasses) {
        ClassLoader classLoader = registry.getClass().getClassLoader();
        return Arrays.stream(factoryClasses)
                .map(
                        factoryClass -> {
                            List<String> factoryImplClassNames = SpringFactoriesLoader.loadFactoryNames(factoryClass, classLoader);
                            return factoryImplClassNames.stream()
                                    .map(
                                            factoryImplClassName -> {
                                                Class<?> factoryImplClass = ClassUtils.resolveClassName(factoryImplClassName, classLoader);
                                                String beanName = Introspector.decapitalize(ClassUtils.getShortName(factoryImplClassName));
                                                boolean result = registerInfrastructureBean(registry, beanName, factoryImplClass);
                                                if (!result) {
                                                    logger.warn(String.format("The Factory Class bean[%s] has been registered with bean name[%s]",
                                                            factoryImplClassName, beanName));

                                                }
                                                return result;
                                            })
                                    .filter(result -> result)
                                    .count();
                        }
                ).mapToInt(Long::intValue).sum();
    }
}
