package org.spring.services;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DeprecatedAnnotationProxyConfigurator implements ProxyConfigurator {
    @Override
    public <T> T configure(Class<T> type, T t) {
        if (type.isAnnotationPresent(Deprecated.class)) {
            return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                    type.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            System.out.println("Do not user deprecated class " + t.getClass());
                            return method.invoke(t, args);
                        }
                    });
        }
        return t;
    }
}
