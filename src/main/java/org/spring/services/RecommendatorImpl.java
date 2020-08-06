package org.spring.services;

@MySingleton
public class RecommendatorImpl implements Recommendator {

    @InjectProperty("alcohol")
    private String drinkName;

    public RecommendatorImpl() {
        System.out.println("Recommendator was created");
    }

    @Override
    public void recommend() {
        System.out.println("drink " + drinkName);
    }
}
