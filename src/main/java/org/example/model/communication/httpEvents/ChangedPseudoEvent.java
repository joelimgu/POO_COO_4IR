package org.example.model.communication.httpEvents;

import org.example.model.conversation.User;

public class ChangedPseudoEvent implements HTTPEvent {
    public User updatedUser;

    public ChangedPseudoEvent(User updatedUser) {
        this.updatedUser = updatedUser;
    }
}
