package org.spring.services;

import javax.annotation.PostConstruct;

@Deprecated
public class UkrainePoliceman implements Policeman {

    @InjectByType
    private Recommendator recommendator;

    @PostConstruct
    public void init() {
        System.out.println("UkrainePoliceman PostConstruct: " + recommendator.getClass());
    }

    @Override
    public void getOutPeople() {
        System.out.println("Геть!");
    }
}
