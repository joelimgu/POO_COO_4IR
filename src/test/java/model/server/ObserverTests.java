package model.server;

import org.example.model.CustomObservable;
import org.example.model.CustomObserver;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObserverTests {
    class O1 implements CustomObservable<Integer> {
        private List<CustomObserver<Integer>> subscribers = new ArrayList<>();
        @Override
        public int subscribe(CustomObserver<Integer> o) {
            this.subscribers.add(o);
            return this.subscribers.size() -1;
        }

        @Override
        public CustomObserver<Integer> unsubscribe(int i) {
            CustomObserver<Integer> o = this.subscribers.get(i);
            this.subscribers.set(i,null);
            return o;
        }

        public void notifySubscriptors(Integer i) {
            this.subscribers.forEach((s) -> {
                if (s == null) {
                    return;
                }
                s.notify(i);
            } );
        }
    }

    class O2 implements CustomObserver<Integer> {
        private Integer value = 0;
        @Override
        public void notify(Integer event) {
            this.value = event;
        }
    }
    @Test
    public void simpleCallback() {
        O1 o1 = new O1();
        O2 o2 = new O2();
        o1.subscribe(o2);
        o1.notifySubscriptors(5);
        assertEquals(5, o2.value);
    }

    @Test
    public void unsubscribe() {
        O1 o1 = new O1();
        O2 o2 = new O2();
        int s = o1.subscribe(o2);
        o1.notifySubscriptors(5);
        assertEquals(5, o2.value);
        o1.unsubscribe(s);
        o1.notifySubscriptors(6);
        assertEquals(5, o2.value);
    }
}
