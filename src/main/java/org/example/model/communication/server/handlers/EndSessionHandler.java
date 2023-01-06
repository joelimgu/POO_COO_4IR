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
import java.nio.charset.StandardCharsets;

public class EndSessionHandler extends BaseHandler implements HttpHandler {
    public EndSessionHandler(@NotNull HTTPServer s){super(s);}

    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            byte [] response = "I'M LEAVING".getBytes();
            HTTPServer.sendResponse(exchange, "User disconnected");
            exchange.sendResponseHeaders(200,response.length);
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            Message m = g.fromJson(exchange.getRequestBody().toString(),Message.class);
            this.httpServer.notifyAllSubscribers(new NewMessageEvent(m));
        }
    }
}
