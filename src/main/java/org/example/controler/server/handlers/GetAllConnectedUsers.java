package org.example.controler.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.controler.server.HTTPServer;
import org.example.model.conversation.ConnectedUser;
import org.example.services.SessionService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class GetAllConnectedUsers extends BaseHandler implements HttpHandler {

    public GetAllConnectedUsers(@NotNull HTTPServer httpServer) {
        super(httpServer);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            List<ConnectedUser> connectedUsers = SessionService.getInstance().getConnectedUsers();
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            String connectedUsersJSON = g.toJson(SessionService.getInstance().getConnectedUsers());
            HTTPServer.sendResponse(exchange, connectedUsersJSON);
        }
    }
}
