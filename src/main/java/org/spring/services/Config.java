package org.spring.services;

public interface Config {
    <T> Class<? extends T> getImplClass(Class<T> type);
}
