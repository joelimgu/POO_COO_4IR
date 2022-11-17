package model.server.Handler;

import com.sun.net.httpserver.*;
import org.example.model.communication.server.HTTPServer;
import org.example.model.conversation.User;
import org.example.services.SessionService;
import org.junit.jupiter.api.Test;
import org.example.model.communication.server.handlers.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserHandlerTest  {

    int port = 12345;
    HTTPServer m_server;

    {
        try {
            m_server = new HTTPServer(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestUserNull() throws IOException, InterruptedException {
  /*      int port = 12345;
        HTTPServer m_server = new HTTPServer(port);
        System.out.println("Server created at " + port);
        */

        SessionService m_session = new SessionService();
        m_session.setM_localUser(null);

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                URI.create("http://localhost:12345/get_user")
        ).build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("body : " + response.body());
        System.out.println(response.statusCode());
        assertEquals(204,response.statusCode());

    }

    @Test
    public void testUserNotnull() throws IOException,InterruptedException{
        /*int port = 1234;
        HTTPServer m_server = new HTTPServer(port);
        System.out.println("Server created at " + port);*/

        User test_user = new User("Asmun");

        SessionService m_session = new SessionService();
        m_session.setM_localUser(test_user);
        System.out.printf(m_session.getM_localUser().getPseudo());
        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(
                URI.create("http://localhost:12345/get_user")
        ).build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("body : " + response.body());
        System.out.println(response.statusCode());
    }

}
