package services;

import org.example.services.HTTPService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HTTPServiceTest {

    @Test
    public void httpRequestPokedex() throws IOException {
        HTTPService m_service = HTTPService.getInstance();
        // "https://pokeapi.co/api/v2/"
        CompletableFuture<HttpResponse<String>> response = m_service.sendRequest("pokeapi.co", "/api/v2/", HTTPService.HTTPMethods.GET, "");
        response.thenAccept((r) -> {
            assertTrue(r.body().contains("ability"));
        });

    }
}
