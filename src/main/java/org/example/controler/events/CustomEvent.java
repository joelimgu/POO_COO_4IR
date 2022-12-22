package org.example.controler.events;

import org.example.services.SessionService;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Subscribe an object with a callback to this event,
 * @apiNote  every instance can only be subscribed once per event. resubscribing will result in replacement
 * @param <T> Argument of the callback functions called
 */
public abstract class CustomEvent<T> {

    private final HashMap<Object,Consumer<T>> callbacks = new HashMap<>();
    private static SessionService instance ;

    private CustomEvent() {
        if (instance != null) {
            throw new RuntimeException("SessionService instanced twice");
        }
    }
    public static <T> SessionService getInstance(){
        if (instance == null){
            synchronized(SessionService.class) {
                if (instance == null) {
                    CustomEvent.instance = new CustomEvent<T>();
                }
            }
        }
        return SessionService.instance;
    }

    public void subscribe(Object o, Consumer<T> consumer) {
        this.callbacks.put(o, consumer);
    }

    public Consumer<T> unsubscribe(Object o) {
        return this.callbacks.remove(o);
    }

    public void notify(T arg) {
        this.callbacks.forEach((o,c) -> c.accept(arg));
    }
}
