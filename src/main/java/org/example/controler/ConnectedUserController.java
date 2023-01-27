package org.example.controler;

import javafx.application.Platform;
import org.example.model.conversation.ConnectedUser;
import org.example.services.HTTPService;
import org.example.services.SessionService;
import org.example.view.HelloApplication;
import org.example.view.HelloController;

import javax.sql.rowset.Joinable;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class ConnectedUserController implements Runnable {

    public void sendPing() throws IOException, InterruptedException {
        while (true) {
            Thread.sleep(Duration.ofSeconds(10).toMillis());
            List<ConnectedUser> m_list = SessionService.getInstance().getRemoteConnectedUsers();
            ListIterator<ConnectedUser> aux = m_list.listIterator();
            while (aux.hasNext()) {
                var test = aux.next();
                String ip = test.getIP();
                if (ip == null){continue;}
                var m_request = HTTPService.getInstance().sendRequest(test.getIP(),"/Send_ping", HTTPService.HTTPMethods.GET,"ping sent");
//                System.out.println("send HTTP request: to " + test.getPseudo() + " and port " + SessionService.getInstance().getHttp_port() + " with users: " + test);
                m_request.exceptionally((e) -> {
                            System.out.println("Removing user from connectedUsers list: " + test.getPseudo());
                            SessionService.getInstance().deleteConnectedUserByName(test.getPseudo());
                            // TODO : Link this to the controller user
                            HelloController hco = HelloApplication.hc;
                            Platform.runLater(() -> {hco.deleteUser(test);});
                            return null;
                        });

            }
        }
    }

    @Override
    public void run() {
        try {
            this.sendPing();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
