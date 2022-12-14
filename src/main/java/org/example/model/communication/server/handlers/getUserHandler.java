package org.example.model.communication.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.model.communication.server.HTTPServer;
import org.example.model.conversation.User;
import org.example.services.SessionService;

import java.io.IOException;
import java.io.OutputStream;

public class getUserHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        SessionService s = SessionService.getInstance();

        if ("GET".equals(httpExchange.getRequestMethod())){
            //todo : search for code of non existing object and how to send the return code
            if (s.getM_localUser() == null)
            {
                sendResponse(httpExchange,"");
            }
            HTTPServer.sendResponse(httpExchange,gson.toJson(s.getM_localUser()));
        }
    }

    public static void sendResponse(HttpExchange httpExchange, String body) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        // todo : modify this function to match the handle one
        // this line is a must
        httpExchange.sendResponseHeaders(204, body.length());
        outputStream.write(body.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
