package org.example.services;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class HTTPService {
    public enum HTTPMethods {
        GET,
        POST,
        PUT,
        DELETE
    }
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

    /**
     * Send HTTP request using the app default port
     * @param IP -> domain or IP without / at the start ex: "192.168.0.14"
     * @param url -> path to ping with slash : "/test"
     * @param method -> HTTP request method GET or POST or DELETE or PUT
     * @param requestBody
     * @return response
     * @throws IOException
     */
    public CompletableFuture<HttpResponse<String>> sendRequest(@NotNull String IP, @NotNull String url, @NotNull HTTPMethods method, @NotNull String requestBody) throws IOException {
        String uri = "http://" + IP + ":" + SessionService.getInstance().getHttp_port() + url;
        var baseRequest = HttpRequest.newBuilder(
                URI.create(uri)
        ).timeout(Duration.ofSeconds(5));
        HttpRequest request = null;
        if (method == HTTPMethods.GET) {
            request = baseRequest.GET().build();
        } else if (method == HTTPMethods.POST) {
            request = baseRequest.POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();
        }
        return this.serv.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }
}
