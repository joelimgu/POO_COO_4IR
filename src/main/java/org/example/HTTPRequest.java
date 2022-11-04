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
                        URI.create("http://localhost:8001/test")
        ).build();

        // use the client to send the request
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // the response:
        System.out.println(response.body());
    }

    }
