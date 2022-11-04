package org.example.model.communication.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.communication.server.Server;

import java.io.IOException;

public class GetPseudo implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            Server.sendResponse(exchange, "Hello world");
        }
    }
}
