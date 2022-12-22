package controler;

import org.example.controler.events.CustomEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaticTest {
    public static int i = 0;
    public static void incr() {
        StaticTest.i += 1;
    }
}

public class CustomEventTests {

    @Test
    public void unsubscribe() {
        CustomEvent<Integer> e = new CustomEvent<>();
        e.subscribe(this,(x) -> {});
        assertNotNull(e.unsubscribe(this));
    }
    @Test
    public void testSubscription() {
        CustomEvent<Integer> e = new CustomEvent<>();
        e.subscribe(this, (x) -> {StaticTest.incr();});
        e.subscribe(this, (x) -> {StaticTest.incr();}); // should overwrite
        e.notify(1);
        assertEquals(1, StaticTest.i);
    }
}

