package model.server;

import org.example.model.conversation.ConnectedUser;
import org.example.services.SessionService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SessionServiceTests {

    @Test
    public void testAddConnectedUser() {
        SessionService ss = SessionService.getInstance();
        ss.getConnectedUsers();
        ss.addConnectedUser(new ConnectedUser("Killian",  UUID.randomUUID(), null));
    }
}
