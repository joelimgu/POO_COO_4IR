package org.example.controler.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.controler.server.HTTPServer;
import org.example.model.communication.httpEvents.ConnectedUsersListReceived;
import org.example.model.conversation.ConnectedUser;
import org.example.services.SessionService;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class receiveConnectedUsersHandler extends BaseHandler implements HttpHandler {
    public receiveConnectedUsersHandler(@NotNull HTTPServer s){super(s);}
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        SessionService s = SessionService.getInstance();
        if ("POST".equals(httpExchange.getRequestMethod())) {
//            byte [] response = "thanks".getBytes();
            String body = new String(httpExchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("received ConnectedUsers: " + body);
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            Type listType = new TypeToken<ArrayList<ConnectedUser>>(){}.getType();
            try {
                List<ConnectedUser> connectedUsers = g.fromJson(body, listType);
                connectedUsers.forEach(s::addConnectedUser);
                System.out.println("calling notify");
                this.httpServer.notifyAllSubscribers(new ConnectedUsersListReceived(connectedUsers));
            } catch (JsonSyntaxException e) {
                System.out.println("Bad JSON formatting in connectedusers[]");
                System.out.println(body);
                e.printStackTrace();
            }
            // TODO: send another error code
            String response = "thanks\n";
            HTTPServer.sendResponse(httpExchange, response);
        } if ("GET".equals((httpExchange.getRequestMethod()))) {
            HTTPServer.sendResponse(httpExchange, "You should POST\n");
        }
    }
}
