package org.example.controler.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.communication.httpEvents.UserDisconnectedEvent;
import org.example.controler.server.HTTPServer;
import org.example.model.conversation.User;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DisconnectHandler extends BaseHandler implements HttpHandler {
    public DisconnectHandler(@NotNull HTTPServer httpServer) {
        super(httpServer);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            try {
                User u = g.fromJson(body, User.class);
                System.out.println("Received disconnection from: " + u);
                this.httpServer.notifyAllSubscribers(new UserDisconnectedEvent(u));
            } catch (JsonSyntaxException e) {
                System.out.println("Bad JSON formatting in disconnect handler");
                System.out.println(body);
                e.printStackTrace();
                // TODO send bad argument error code
            }
            HTTPServer.sendResponse(exchange, "Ok bye!");
        }
    }
}
