package org.example.controler;

import org.example.model.CustomObserver;
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

public class StartSessionController implements CustomObserver<HTTPEvent> {

    private CompletableFuture<List<ConnectedUser>> f;
    public void verifyPseudo(String pseudo) throws IOException {
        SessionService.getInstance().getHttpServer().subscribe(this);
        UDPBroadcast.broadcastUDP bc = new UDPBroadcast.broadcastUDP();
        bc.sendBroadcast("coucou", SessionService.getInstance().getUdp_port());
        this.f = new CompletableFuture<List<ConnectedUser>>().thenApply((c) -> {
            this.connexionOK(c);
            return c;
        });
        this.f.completeOnTimeout(new ArrayList<>(), 5, TimeUnit.SECONDS);
    }

    public void connexionOK(List<ConnectedUser> c) {
        System.out.println("Sessions started, connected users = " + c);
    }
    @Override
    public void notify(HTTPEvent event) {
        if (event.getClass() !=  ConnectedUsersListReceived.class) {
            return;
        }
        this.f.complete(((ConnectedUsersListReceived) event).connectedUsers);
        // TODO: merge instead of set
//        SessionService.getInstance().setConnectedUsers(event);
        try {
            ConnectedUser connectedUser = SessionService.getInstance().getConnectedUsers().get(0);
            HTTPService.getInstance().sendRequest(connectedUser.getIP() + SessionService.getInstance());
        } catch (IndexOutOfBoundsException e) {
            // TODO: on n'a pas d'internet ou pas de users dans le rzo (INSERT ERROR DIALOG SI PAS INTERNET)
        } catch (IOException e) {
            // TODO: Throw Pop up to indicate connexion HTTP error
        }
    }
}
