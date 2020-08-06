package org.spring.services;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactory {
    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private ApplicationContext context;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.context = context;

        Set<Class<? extends ObjectConfigurator>> classes = context.getScanner().getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> aClass : classes) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        T t = type.getDeclaredConstructor().newInstance();

        configure(t);
        return t;
    }

    private <T> void configure(T t) {
        configurators.forEach(configurator -> configurator.configure(t, context));
    }
}
