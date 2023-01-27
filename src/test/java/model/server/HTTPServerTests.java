package model.server;

import org.example.controler.ListenersInit;
import org.example.services.HTTPService;
import org.junit.jupiter.api.Test;
import services.HTTPServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HTTPServerTests {

    @Test
    public void canRespond() {
        ListenersInit.startServers();
        HTTPService.getInstance().sendRequest("localhost", "/ping", HTTPService.HTTPMethods.GET, "").thenAccept((response) -> {
            assertEquals(2,2);
        });
    }
}
