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

    // TODO: pass the port in the argument
    private static void startUDPServer() {
        UDPReceive udp = new UDPReceive();
        new Thread(udp).start();
        SessionService.getInstance().setUDPServer(udp);
    }
    public static void startServers() {
//        HTTPService.getInstance();
        try {
            startHTTPServer(SessionService.getInstance().getHttp_port());
            startUDPServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
