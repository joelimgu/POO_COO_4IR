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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class receiveConnectedUsersHandler extends BaseHandler implements HttpHandler {
    public receiveConnectedUsersHandler(@NotNull HTTPServer s){super(s);}
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        SessionService s = SessionService.getInstance();
        if ("POST".equals(httpExchange.getRequestMethod())) {
            byte [] response = "thanks".getBytes();
            String body = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("received ConnectedUsers: " + body);
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            Type listType = new TypeToken<ArrayList<ConnectedUser>>(){}.getType();
            List<ConnectedUser> connectedUsers = g.fromJson(body, listType);
            // TODO: merge instead of set
            connectedUsers.forEach(s::addConnectedUser);
            this.httpServer.notifyAllSubscribers(new ConnectedUsersListReceived(connectedUsers));
            HTTPServer.sendResponse(httpExchange, Arrays.toString(response));
            httpExchange.sendResponseHeaders(200, response.length);
        }
    }
}
