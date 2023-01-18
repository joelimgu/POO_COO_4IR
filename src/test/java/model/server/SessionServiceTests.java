package model.server;

import org.example.model.conversation.ConnectedUser;
import org.example.services.HTTPService;
import org.example.services.SessionService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SessionServiceTests {

    @Test
    public void testaddConnectedUser() {
        SessionService ss = SessionService.getInstance();
        ss.getConnectedUsers();
        ss.addConnectedUser(new ConnectedUser("Killian", new UUID()));
    }

}
