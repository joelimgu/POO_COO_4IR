package org.example.model.communication.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.communication.server.HTTPServer;
import org.example.model.communication.server.httpEvents.NewMessageEvent;
import org.example.model.conversation.Message;
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
