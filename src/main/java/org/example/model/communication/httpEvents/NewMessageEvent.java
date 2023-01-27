package org.example.model.communication.httpEvents;

import org.example.model.conversation.Message;

public class NewMessageEvent implements HTTPEvent {

    private Message m;
    public NewMessageEvent(Message m) {
        this.m = m;
    }
    public Message getM() { return m; }
}
