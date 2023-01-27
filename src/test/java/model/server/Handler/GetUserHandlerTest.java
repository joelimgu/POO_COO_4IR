package model.server.Handler;

import org.example.controler.server.HTTPServer;
import org.example.services.LoggerService;
import org.example.services.SessionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserHandlerTest  {

    public static int port = 4000;
    public static HTTPServer m_server;
    @BeforeAll
    public static void initiateHttpServer() {
        try {
            GetUserHandlerTest.m_server = new HTTPServer(port);
        } catch (IOException e) {
            LoggerService.getInstance().log("unable to start HTTPServer");
            e.printStackTrace();
        }
    }

    @Test
    public void TestUserNull() throws IOException {
  /*      int port = 12345;
        HTTPServer m_server = new HTTPServer(port);
        LoggerService.getInstance().log("Server created at " + port);
        */

        SessionService m_session = SessionService.getInstance();
        m_session.setM_localUser(null);

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                URI.create("http://localhost:"+port+"/get_user")
        ).build();

        HttpResponse<String> response;

    }


}
