package org.example.controler;

import org.example.model.CustomObserver;
import org.example.model.communication.server.UDPBroadcast;
import org.example.model.conversation.ConnectedUser;
import org.example.services.HTTPService;
import org.example.services.SessionService;

import java.io.IOException;
import java.util.List;

public class StartSessionController implements CustomObserver<List<ConnectedUser>> {
    public void verifyPseudo(String pseudo) throws IOException {
        ListenersInit.startServers();
        SessionService.getInstance().getUDPServer().subscribe(this);
        UDPBroadcast bc = new UDPBroadcast();
    }

    @Override
    public void notify(List<ConnectedUser> event) {
        SessionService.getInstance().setConnectedUsers(event);
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
