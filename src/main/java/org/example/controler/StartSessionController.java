package org.example.controler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.HTTPRequest;
import org.example.model.CustomObserver;
import org.example.model.communication.server.HTTPServer;
import org.example.model.communication.server.UDPBroadcast;
import org.example.model.communication.server.httpEvents.ConnectedUsersListReceived;
import org.example.model.communication.server.httpEvents.HTTPEvent;
import org.example.model.conversation.ConnectedUser;
import org.example.services.HTTPService;
import org.example.services.SessionService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class StartSessionController {

    private CompletableFuture<HTTPEvent> f;
    public void startSession(String pseudo) throws IOException {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        UDPBroadcast.broadcastUDP bc = new UDPBroadcast.broadcastUDP();
        SessionService.getInstance().setM_localUser(new ConnectedUser(pseudo, null));
        this.f = new CompletableFuture<>();
        this.f.completeOnTimeout(new ConnectedUsersListReceived(new ArrayList<>()), 2, TimeUnit.SECONDS);
        SessionService s = SessionService.getInstance();
        s.getHttpServer().addEventList(f);
        bc.sendBroadcast("coucou", s.getUdp_port());
        this.f.join();
        System.out.println("jonied future");
        this.f.thenAccept((event) -> {
            if (event.getClass() ==  ConnectedUsersListReceived.class) {
                addAllnewUsers((ConnectedUsersListReceived) event);
            }
        });

        System.out.println("Sending to all users: " + s.getConnectedUsers());
        s.getRemoteConnectedUsers().forEach((u) -> {
            String json = g.toJson(s.getConnectedUsers());
            HTTPService.getInstance()
                    .sendRequest(u.getIP(),"/receive_connected_users_list", HTTPService.HTTPMethods.POST, json)
                            .thenAccept((r) -> System.out.println("Sent connected users on login: " + u.getPseudo()))
                    .exceptionally((e) -> {
                        System.out.println("Error sending http to " + u.getIP());
                        e.printStackTrace();
                        return null;
                    });
        });
        System.out.println("Pseudo verified");
    }

    private static void addAllnewUsers(ConnectedUsersListReceived event) {
        event.connectedUsers.forEach((u) -> {
            SessionService.getInstance().addConnectedUser(u);
        });
    }
}
