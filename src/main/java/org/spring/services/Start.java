package org.spring.services;

import java.util.HashMap;
import java.util.Map;

public class Start {

    public static void main(String[] args) {
        Map<Class, Class> map = new HashMap<>(Map.of(Policeman.class, UkrainePoliceman.class));

        ApplicationContext context = ApplicationRunner.run("org.spring.services", map);

        CoronaDesinfector coronaDesinfector = context.getObject(CoronaDesinfector.class);
        coronaDesinfector.start(new Room());
    }
}
