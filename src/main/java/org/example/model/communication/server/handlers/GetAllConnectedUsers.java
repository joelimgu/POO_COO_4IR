package org.example.model.communication.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.communication.server.HTTPServer;
import org.example.model.conversation.ConnectedUser;
import org.example.services.SessionService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetAllConnectedUsers extends BaseHandler implements HttpHandler {

    public GetAllConnectedUsers(@NotNull HTTPServer httpServer) {
        super(httpServer);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            List<ConnectedUser> connectedUsers = new ArrayList<>(SessionService.getInstance().getConnectedUsers().values());
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            String connectedUsersJSON = g.toJson(SessionService.getInstance().getConnectedUsers());
            HTTPServer.sendResponse(exchange, connectedUsersJSON);
        }
    }
}
