package org.example.model.conversation;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Message {
    private Date sendTime = new Date();
    private String text;

    private User sender;

    public Message(@NotNull User sender, @NotNull String text, @NotNull Date sendTime) {
        this.sendTime = sendTime;
        this.text = text;
        this.sender = sender;
    }

    public Message(@NotNull User sender, @NotNull String text) {
        this.text = text;
        this.sender = sender;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
