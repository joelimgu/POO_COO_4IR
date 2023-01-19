package org.example.model.communication.server.httpEvents;

import org.example.model.conversation.User;

public class UserDisconnectedEvent implements HTTPEvent {
    public User u;
    public UserDisconnectedEvent(User u) {
        this.u = u;
    }
}
