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
import java.util.List;

public class getIPHandler implements HttpHandler {
    protected HTTPServer httpServer;
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            // getRemoteAddress retunrs something of the form "/192.168.43.175:53858" and we just want the IP
            String host = exchange.getRemoteAddress().toString().replace("/","").split(":")[0];
            System.out.println("HOST: " + host);

            HTTPServer.sendResponse(exchange, host);
        }
    }
    public getIPHandler(@NotNull HTTPServer httpServer) {
        this.httpServer = httpServer;
    }
}
