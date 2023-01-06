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

public class ReceiveMessageHandler extends BaseHandler implements HttpHandler {
    public ReceiveMessageHandler(@NotNull HTTPServer s) {
        super(s);
    }

    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            byte [] response = "CONNECTED".getBytes();
            HTTPServer.sendResponse(exchange, "I'm connected");
            exchange.sendResponseHeaders(200, response.length);
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            Message m = g.fromJson(exchange.getRequestBody().toString(), Message.class);
            this.httpServer.notifyAllSubscribers(new NewMessageEvent(m));
        }
    }
}
