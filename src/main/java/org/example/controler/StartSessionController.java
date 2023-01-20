package org.example.controler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.communication.server.UDPBroadcast;
import org.example.model.communication.server.httpEvents.ConnectedUsersListReceived;
import org.example.model.communication.server.httpEvents.HTTPEvent;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.User;
import org.example.services.HTTPService;
import org.example.services.SessionService;
import org.example.services.StorageService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class StartSessionController {

    private CompletableFuture<HTTPEvent> f;
    private ConnectedUserController m_ping = new ConnectedUserController();
    public void startSession(String pseudo) throws IOException, InterruptedException {

        Gson g = new GsonBuilder().setPrettyPrinting().create();
        StorageService bd = StorageService.getInstance();
        SessionService s = SessionService.getInstance();
        try {
            List<User> lu = bd.getUserFromPseudo(pseudo);
            if (!lu.isEmpty()) {
                User u = lu.get(0);
                s.setM_localUser(new ConnectedUser(u.getPseudo(), u.getUuid(), null));
            } else {
                SessionService.getInstance().setM_localUser(new ConnectedUser(pseudo, null));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Trying to log in with user: " + s.getM_localUser().getPseudo() + " and uuid: " + s.getM_localUser().getUuid());
        UDPBroadcast.broadcastUDP bc = new UDPBroadcast.broadcastUDP();
        this.f = new CompletableFuture<>();
        this.f.completeOnTimeout(new ConnectedUsersListReceived(new ArrayList<>()), 2, TimeUnit.SECONDS);
        s.getHttpServer().addEventList(f);
        bc.sendBroadcast("coucou", s.getUdp_port());
        this.f.join();
        System.out.println("joined future");
        this.f.thenAccept((event) -> {
            if (event.getClass() ==  ConnectedUsersListReceived.class) {
                addAllNewUsers((ConnectedUsersListReceived) event);
            }
        });
        if (isUnique(pseudo)) {
            try {
                String connectedUserIp = s.getRemoteConnectedUsers().get(0).getIP();
                String IP = HTTPService.getInstance()
                        .sendRequest(
                                connectedUserIp,
                                "/get_self_ip",
                                HTTPService.HTTPMethods.GET,
                                ""
                        ).join().body();
                s.setLocalIP(IP);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("I'm alone so I cant get my IP");
            }
            try {
                StorageService.getInstance().save(s.getM_localUser());
            } catch (SQLException e) {
                throw new RuntimeException("Couldnt save the user to db", e);
            }

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
