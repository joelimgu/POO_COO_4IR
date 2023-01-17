package org.example.controler;

import org.example.model.conversation.ConnectedUser;
import org.example.services.HTTPService;
import org.example.services.SessionService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ListIterator;

import static java.lang.Thread.sleep;

public class ConnectedUserController {

    public void sendPing() throws IOException, InterruptedException {
        while (true) {
            sleep(10);
            List<ConnectedUser> m_list = SessionService.getInstance().getConnectedUsers();
            ListIterator<ConnectedUser> aux = m_list.listIterator();
            while (aux.hasNext()) {
                String url = aux.next().getIP() + "/Send_ping";
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                        .headers("Ping_Request").POST(HttpRequest.BodyPublishers.ofString(String.valueOf(aux.next()))).build();
                HTTPService.getInstance().sendRequest("Ping sent");
                HTTPService.getInstance().getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("send HTTP request: to " + aux.next().getPseudo() + " and port " + SessionService.getInstance().getHttp_port() + " with users: " + aux.next());
                HTTPService.getInstance().getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .exceptionally((e) -> {
                            // TODO log e
                            System.out.println("Removing user from connectedUsers list: " + e);
                            aux.remove();
                            return null;
                        });

            }
        }
    }
}
