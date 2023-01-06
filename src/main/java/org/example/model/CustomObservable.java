package org.example.model;

public interface CustomObservable<T> {
    int subscribe(CustomObserver<T> o);
    CustomObserver<T> unsubscribe(int i);
}
