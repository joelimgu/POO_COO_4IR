package org.example.model.communication.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import org.example.model.communication.server.HTTPServer;

import java.io.IOException;

public class EndSessionHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            HTTPServer.sendResponse(exchange, "User disconnected");
        }
    }
}
