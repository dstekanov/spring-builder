package org.spring.services;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactory {
    private ApplicationContext context;
    private List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;

        Set<Class<? extends ObjectConfigurator>> objectConfClasses = context.getScanner().getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> aClass : objectConfClasses) {
            objectConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
        Set<Class<? extends ProxyConfigurator>> proxyConfClasses = context.getScanner().getSubTypesOf(ProxyConfigurator.class);
        for (Class<? extends ProxyConfigurator> aClass : proxyConfClasses) {
            proxyConfigurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        T t = type.getDeclaredConstructor().newInstance();

        configure(t);

        invokeInit(type, t);

        t = configureProxy(type, t);

        return t;
    }

    private <T> void invokeInit(Class<T> type, T t) throws IllegalAccessException, InvocationTargetException {
        for (Method method : type.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }

    private <T> void configure(T t) {
        objectConfigurators.forEach(configurator -> configurator.configure(t, context));
    }

    private <T> T configureProxy(Class<T> type, T t) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            t = proxyConfigurator.configure(type, t);
        }
        return t;
    }
}
