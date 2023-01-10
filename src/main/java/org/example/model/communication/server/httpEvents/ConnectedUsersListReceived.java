package org.example.model.communication.server.httpEvents;

import org.example.model.conversation.ConnectedUser;

import java.util.List;

public class ConnectedUsersListReceived extends HTTPEvent {
    public List<ConnectedUser> connectedUsers;

    public ConnectedUsersListReceived(List<ConnectedUser> c) {
        this.connectedUsers = c;
    }
}
