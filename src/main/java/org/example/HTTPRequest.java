package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPRequest {
    public static void main(String[] args) throws IOException, InterruptedException {
        // create a client
        var client = HttpClient.newHttpClient();

        // create a request
        var request = HttpRequest.newBuilder(
                        URI.create("http://192.168.43.205:3000/receive_connected_users_list")
        ).POST(HttpRequest.BodyPublishers.ofString("[]")).build();

        // use the client to send the request
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // the response:
        System.out.println(response.body());
//        System.out.printf(response.statusCode());
    }
    
    // TODO: 1/5/23 ping each 10 s to check if the user is still connected
    // TODO: 1/5/23 receive message (launch an event for a notification)
    // TODO: 1/5/23 End session Handler (delete the user that got disconnected)

}
