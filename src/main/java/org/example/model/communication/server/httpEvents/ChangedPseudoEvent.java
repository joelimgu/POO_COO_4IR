package org.example.model.communication.server.httpEvents;

import org.example.model.conversation.User;

import java.util.UUID;

public class ChangedPseudoEvent implements HTTPEvent {
    public User updatedUser;

    public ChangedPseudoEvent(User updatedUser) {
        this.updatedUser = updatedUser;
    }
}
