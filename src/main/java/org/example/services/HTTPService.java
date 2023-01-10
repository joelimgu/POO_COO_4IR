package org.example.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HTTPService {

    private static volatile HTTPService instance;
    private String url;
    private final HttpClient serv;

    private HTTPService() {
        this.serv = HttpClient.newHttpClient();
        if (instance != null) {
            throw new RuntimeException("SessionService instanced twice");
        }
    }

    synchronized public static HTTPService getInstance() {
        if (instance == null){
            synchronized(SessionService.class) {
                if (instance == null) {
                    instance = new HTTPService();
                }
            }
        }
        return instance;
    }

    public HttpClient getClient() {
        return this.serv;
    }

    public HttpResponse<String> sendRequest(String url) throws IOException {
        HttpResponse<String> response = null;
        var request = HttpRequest.newBuilder(
                URI.create(url)
        ).build();
        try {
            response = this.serv.send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
