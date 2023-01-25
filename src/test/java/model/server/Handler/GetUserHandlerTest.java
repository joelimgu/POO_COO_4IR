package model.server.Handler;

import org.example.model.communication.server.HTTPServer;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.User;
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
            System.out.println("unable to start HTTPServer");
            e.printStackTrace();
        }
    }

    @Test
    public void TestUserNull() throws IOException {
  /*      int port = 12345;
        HTTPServer m_server = new HTTPServer(port);
        System.out.println("Server created at " + port);
        */

        SessionService m_session = SessionService.getInstance();
        m_session.setM_localUser(null);

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                URI.create("http://localhost:"+port+"/get_user")
        ).build();

        HttpResponse<String> response;
//        try {
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            System.out.println("body : " + response.body());
//            System.out.println(response.statusCode());
//            assertEquals(204,response.statusCode());
//
//        } catch (InterruptedException e) {
//            System.out.println("unable to send message");
//            e.printStackTrace();
//        }


    }

    // TODO see why it doesnt work
//    @Test
//    public void testUserNotnull() throws IOException,InterruptedException{
//        /*int port = 1234;
//        HTTPServer m_server = new HTTPServer(port);
//        System.out.println("Server created at " + port);*/
//
//        User test_user = new User("Asmun");
//
//        SessionService m_session = SessionService.getInstance();
//        m_session.setM_localUser(new ConnectedUser(test_user.getPseudo(), test_user.getUuid(), null);
//        System.out.printf(m_session.getM_localUser().getPseudo());
//        var client = HttpClient.newHttpClient();
//
//        var request = HttpRequest.newBuilder(
//                URI.create("http://localhost:"+port+"/get_user")
//        ).build();
//
//        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println("body : " + response.body());
//        System.out.println(response.statusCode());
//    }

}
