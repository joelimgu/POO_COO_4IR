package org.example.controler.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.controler.server.HTTPServer;
import org.example.services.LoggerService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class getIPHandler implements HttpHandler {
    protected HTTPServer httpServer;
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            // getRemoteAddress retunrs something of the form "/192.168.43.175:53858" and we just want the IP
            String host = exchange.getRemoteAddress().toString().replace("/","").split(":")[0];
            LoggerService.getInstance().log("HOST: " + host);

            HTTPServer.sendResponse(exchange, host);
        }
    }
    public getIPHandler(@NotNull HTTPServer httpServer) {
        this.httpServer = httpServer;
    }
}
