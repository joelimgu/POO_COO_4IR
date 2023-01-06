package org.example.model.communication.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.communication.server.HTTPServer;
import org.example.services.SessionService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ConnectedUserHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        SessionService s = SessionService.getInstance();

        if ("POST".equals(httpExchange.getRequestMethod())) {
            byte [] response = "CONNECTED".getBytes();
            HTTPServer.sendResponse(httpExchange, "I'm connected");
            httpExchange.sendResponseHeaders(200, response.length);
        }
    }
}
