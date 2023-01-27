package org.example.controler.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.controler.server.HTTPServer;
import org.example.model.communication.httpEvents.NewMessageEvent;
import org.example.model.conversation.Message;
import org.example.services.LoggerService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ReceiveMessageHandler extends BaseHandler implements HttpHandler {
    public ReceiveMessageHandler(@NotNull HTTPServer s) {
        super(s);
    }

    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            Gson g = new GsonBuilder().setPrettyPrinting().create();
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            try {
                Message m = g.fromJson(body, Message.class);
                LoggerService.getInstance().log("Received message: " + m);
                this.httpServer.notifyAllSubscribers(new NewMessageEvent(m));
            } catch (JsonSyntaxException e) {
                LoggerService.getInstance().log("Bad JSON formatting in receve message[]");
                LoggerService.getInstance().log(body);
                e.printStackTrace();
            }
            HTTPServer.sendResponse(exchange, "I'm connected\n");
        }
    }
}
