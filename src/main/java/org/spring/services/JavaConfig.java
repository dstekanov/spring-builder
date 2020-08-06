package org.spring.services;

import lombok.AllArgsConstructor;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {

    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass;

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) {
        return ifc2ImplClass.computeIfAbsent(type, aClass -> {
            Set<Class<? extends T>> set = scanner.getSubTypesOf(type);
            if (set.size() != 1) {
                throw new IllegalArgumentException(type + " has 0 or more than one impl.");
            }
            return set.iterator().next();
        });
    }
}
