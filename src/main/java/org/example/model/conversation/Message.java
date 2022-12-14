package org.example.model.conversation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.UUID;

public class Message {
    private Date sendTime = new Date();
    private String text;

    private User sender;
    private User receiver;
    private final UUID uuid;

    public Message(@NotNull UUID uuid, @NotNull User sender, @NotNull User receiver, @NotNull String text, @NotNull Date sendTime) {
        this.sendTime = sendTime;
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
        this.uuid = uuid;
    }

    public Message(@NotNull User sender, @NotNull User receiver, @NotNull String text) {
        this.text = text;
        this.sender = sender;
        this.receiver = receiver;
        this.uuid = UUID.randomUUID(); // v4
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

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        return g.toJson(this);
    }
}
