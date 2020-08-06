package org.spring.services;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    @Getter
    private Reflections scanner;
    private Config config;
    private ObjectFactory factory;

    public ApplicationContext(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        scanner = new Reflections(packageToScan);
        config = new JavaConfig(scanner, ifc2ImplClass);
        factory = new ObjectFactory(this);
    }

    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        Class<T> implClass = resolveImpl(type);
        T t = factory.createObject(implClass); // type or implClass ?

        if (implClass.isAnnotationPresent(MySingleton.class)) {
            cache.put(type, t);
        }

        return t;
    }

    private <T> Class<T> resolveImpl(Class<T> type) {
        if (type.isInterface()) {
            type = (Class<T>) config.getImplClass(type);
        }
        return type;
    }

}
