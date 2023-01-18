package org.example.controler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.HTTPRequest;
import org.example.model.CustomObserver;
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
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class StartSessionController implements CustomObserver<HTTPEvent> {

    private CompletableFuture<List<ConnectedUser>> f;
    public void startSession(String pseudo) throws IOException {
        SessionService.getInstance().getHttpServer().subscribe(this);
        UDPBroadcast.broadcastUDP bc = new UDPBroadcast.broadcastUDP();
        SessionService.getInstance().setM_localUser(new ConnectedUser(pseudo, null));
        bc.sendBroadcast("coucou", SessionService.getInstance().getUdp_port());
        this.f = new CompletableFuture<>();
        this.f.completeOnTimeout(new ArrayList<>(), 1, TimeUnit.SECONDS);
        this.f.join();
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        SessionService.getInstance().getConnectedUsers().forEach((u) -> {
            String url = u.getIP() + "/receive_connected_users_list";
            String json = g.toJson(SessionService.getInstance().getConnectedUsers());
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://" + url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            System.out.println("Sent connected users on login: " + u.getPseudo());
            try {
                HTTPService.getInstance().getClient().send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {
                System.out.println("IO exception http 2");
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Pseudo verified");
    }

    public void connexionOK(List<ConnectedUser> c) {
        System.out.println("Sessions started, connected users = " + c);

        // TODO: change window
    }
    @Override
    public void notify(HTTPEvent event) {
        if (event.getClass() !=  ConnectedUsersListReceived.class) {
            return;
        }
        System.out.println("notify with users: " + ((ConnectedUsersListReceived) event).connectedUsers);
        this.f.complete(((ConnectedUsersListReceived) event).connectedUsers);
        // TODO: merge instead of set
//        SessionService.getInstance().setConnectedUsers(event);
//        try {
//            ConnectedUser connectedUser = SessionService.getInstance().getConnectedUsers().get(0);
////            HTTPService.getInstance().sendRequest(connectedUser.getIP() + SessionService.getInstance());
//        } catch (IndexOutOfBoundsException e) {
//            System.out.println("catch session controler notify IndexOutOfBoundsException");
//            e.printStackTrace();
//            // TODO: on n'a pas d'internet ou pas de users dans le rzo (INSERT ERROR DIALOG SI PAS INTERNET)
//        } catch (IOException e) {
//            // TODO: Throw Pop up to indicate connexion HTTP error
//        }
    }
}
