package org.example.model.communication.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.communication.server.HTTPServer;
import org.example.model.communication.server.httpEvents.ConnectedUsersListReceived;
import org.example.model.communication.server.httpEvents.NewMessageEvent;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.Message;
import org.example.services.SessionService;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class receiveConnectedUsersHandler extends BaseHandler implements HttpHandler {
    public receiveConnectedUsersHandler(@NotNull HTTPServer s){super(s);}
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        SessionService s = SessionService.getInstance();
        if ("POST".equals(httpExchange.getRequestMethod())) {
            byte [] response = "".getBytes();
            HTTPServer.sendResponse(httpExchange, "thanks");
            httpExchange.sendResponseHeaders(200, response.length);
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            Type listType = new TypeToken<ArrayList<ConnectedUser>>(){}.getType();
            List<ConnectedUser> connectedUsers = g.fromJson(httpExchange.getRequestBody().toString(), listType);
            // TODO: merge instead of set
            s.setConnectedUsers(connectedUsers);
            this.httpServer.notifyAllSubscribers(new ConnectedUsersListReceived(connectedUsers));
        }
    }
}
