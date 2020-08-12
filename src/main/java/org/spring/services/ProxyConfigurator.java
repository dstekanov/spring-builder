package org.spring.services;

public interface ProxyConfigurator {
    <T> T configure(Class<T> type, T t);
}
