package org.example.controler.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.communication.httpEvents.ChangedPseudoEvent;
import org.example.controler.server.HTTPServer;
import org.example.model.conversation.User;
import org.example.services.LoggerService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UpdatePseudoHandler extends BaseHandler implements HttpHandler {

    public UpdatePseudoHandler(@NotNull HTTPServer httpServer) {
        super(httpServer);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("PUT".equals(exchange.getRequestMethod())) {
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            try {
                User u = g.fromJson(body, User.class);
                LoggerService.getInstance().log("Received update pseudo from: " + u);
                this.httpServer.notifyAllSubscribers(new ChangedPseudoEvent(u));
            } catch (JsonSyntaxException e) {
                LoggerService.getInstance().log("Bad JSON formatting in updatePseudo handler");
                LoggerService.getInstance().log(body);
                e.printStackTrace();
                // TODO send bad argument error code
            }
            HTTPServer.sendResponse(exchange, "Pseudo Updated");
        }
    }
}
