package services;

import org.example.services.HTTPService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HTTPServiceTest {

    @Test
    public void httpRequestPokedex() throws IOException {
        HTTPService m_service = HTTPService.getInstance("https://pokeapi.co/api/v2/");
        HttpResponse<String> response = m_service.sendRequest("https://pokeapi.co/api/v2/");
        assertTrue(response.body().contains("ability"));
    }
}
