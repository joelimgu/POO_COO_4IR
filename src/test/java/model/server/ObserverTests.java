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
            return null;
        }

        public void notifySubscriptors(Integer i) {
            this.subscribers.forEach((s) -> s.notify(i) );
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
}
