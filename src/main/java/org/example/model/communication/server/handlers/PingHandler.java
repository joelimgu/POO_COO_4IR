package org.example.model.communication.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import org.example.model.communication.server.HTTPServer;

import java.io.IOException;

public class PingHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            byte [] response = "PING RECEIVED".getBytes();
            HTTPServer.sendResponse(exchange, "I'm Still here !");
            exchange.sendResponseHeaders(200, response.length);
        }
    }
}
