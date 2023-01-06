package org.example.controler;

import org.example.model.communication.server.HTTPServer;
import org.example.model.communication.server.UDPReceive;
import org.example.services.HTTPService;
import org.example.services.SessionService;

import java.io.IOException;

public class ListenersInit {

    private static void startHTTPServer(int port) throws IOException {
        HTTPServer http = new HTTPServer(port);
        SessionService.getInstance().setHttpServer(http);
    }

    private static void startUDPServer() {
        UDPReceive udp = new UDPReceive();
        SessionService.getInstance().setUDPServer(udp);
    }
    public static void startServers() throws IOException {
        int port = 5400;
        HTTPService.getInstance();
        startHTTPServer(5400);
        startUDPServer();
    }
}
