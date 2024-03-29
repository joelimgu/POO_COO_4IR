package org.example.controler.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.controler.server.HTTPServer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class PingHandler extends BaseHandler implements HttpHandler {
    public PingHandler(@NotNull HTTPServer s){super(s);}
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            HTTPServer.sendResponse(exchange, "Yes, I'm here");
        }
    }
}
