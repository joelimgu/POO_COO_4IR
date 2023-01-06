package org.example.model;

public interface CustomObserver<T> {
    void notify(T event);
}
