package org.example.model.communication.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import org.example.model.communication.server.HTTPServer;

import java.io.IOException;

public class ReceiveMessageHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            byte [] response = "CONNECTED".getBytes();
            HTTPServer.sendResponse(exchange, "I'm connected");
            exchange.sendResponseHeaders(200, response.length);
        }
    }
}
