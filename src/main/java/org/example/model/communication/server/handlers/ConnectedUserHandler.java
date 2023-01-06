package org.example.model.communication.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.communication.server.HTTPServer;
import org.example.model.communication.server.httpEvents.NewMessageEvent;
import org.example.model.conversation.Message;
import org.example.services.SessionService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ConnectedUserHandler extends BaseHandler implements HttpHandler {
    public ConnectedUserHandler(@NotNull HTTPServer s){super(s);}
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        SessionService s = SessionService.getInstance();
        if ("POST".equals(httpExchange.getRequestMethod())) {
            byte [] response = "CONNECTED".getBytes();
            HTTPServer.sendResponse(httpExchange, "I'm connected");
            httpExchange.sendResponseHeaders(200, response.length);
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            Message m = g.fromJson(httpExchange.getRequestBody().toString(), Message.class);
            this.httpServer.notifyAllSubscribers(new NewMessageEvent(m));
        }
    }
}
