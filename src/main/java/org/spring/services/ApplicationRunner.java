package org.spring.services;

import java.util.Map;

public class ApplicationRunner {

    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifc2ImplClass) {
        ApplicationContext context = new ApplicationContext(packageToScan, ifc2ImplClass);
        return context;
    }
}
