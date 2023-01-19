package org.example.controler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.communication.server.UDPBroadcast;
import org.example.model.communication.server.httpEvents.ConnectedUsersListReceived;
import org.example.model.communication.server.httpEvents.HTTPEvent;
import org.example.model.conversation.ConnectedUser;
import org.example.services.HTTPService;
import org.example.services.SessionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class StartSessionController {

    private CompletableFuture<HTTPEvent> f;
    private ConnectedUserController m_ping = new ConnectedUserController();
    public void startSession(String pseudo) throws IOException, InterruptedException {

        Gson g = new GsonBuilder().setPrettyPrinting().create();
        UDPBroadcast.broadcastUDP bc = new UDPBroadcast.broadcastUDP();
        SessionService.getInstance().setM_localUser(new ConnectedUser(pseudo, null));
        this.f = new CompletableFuture<>();
        this.f.completeOnTimeout(new ConnectedUsersListReceived(new ArrayList<>()), 2, TimeUnit.SECONDS);
        SessionService s = SessionService.getInstance();
        s.getHttpServer().addEventList(f);
        bc.sendBroadcast("coucou", s.getUdp_port());
        this.f.join();
        System.out.println("joined future");
        this.f.thenAccept((event) -> {
            if (event.getClass() ==  ConnectedUsersListReceived.class) {
                addAllNewUsers((ConnectedUsersListReceived) event);
            }
        });
        if (isUnique(pseudo)==true) {
            System.out.println("Sending to all users: " + SessionService.getInstance().getConnectedUsers());
            s.getRemoteConnectedUsers().forEach((u) -> {
                String json = g.toJson(s.getConnectedUsers());
                if (u.getIP() == null) {
                    return;
                }
                HTTPService.getInstance()
                        .sendRequest(u.getIP(),"/receive_connected_users_list", HTTPService.HTTPMethods.POST, json)
                        .thenAccept((r) -> System.out.println("Sent connected users on login: " + u.getPseudo()))
                        .exceptionally((e) -> {
                            System.out.println("Error sending http to " + u.getIP());
                            e.printStackTrace();
                            return null;
                        });
            });
            new Thread(m_ping).start();
            System.out.println("Pseudo verified");
        }
        else{
            System.out.println("Please chose another pseudo, already used !");
            throw new IOException();
        }

    }

    private static void addAllNewUsers(ConnectedUsersListReceived event) {
        event.connectedUsers.forEach((u) -> {
            SessionService.getInstance().addConnectedUser(u);
        });
    }

    public boolean isUnique (String pseudo) {
        List<ConnectedUser> m_list = SessionService.getInstance().getRemoteConnectedUsers();
        for (ConnectedUser aux: m_list) {if (pseudo.equals(aux.getPseudo())){return false;}}
        return true;
    }
}
