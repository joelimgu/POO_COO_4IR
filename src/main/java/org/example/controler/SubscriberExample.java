package org.example.controler;

import java.util.concurrent.Flow;

public class SubscriberExample implements Flow.Subscriber {

    public void SubscriberExample() {

    }
    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(Object item) {
        System.out.println("subscription called");
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
