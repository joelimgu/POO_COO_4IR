package org.example.model.conversation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conversation implements Serializable {

    private List<Message> messages = new ArrayList<>();

    private final User user1;

    public Conversation (@NotNull User user) {
        this.user1 = user;
    }

    // TODO, should be privade and be called from the other constructor
    public Conversation (@NotNull User user, @NotNull List<Message> h) {
        this.user1 = user;
        this.messages = h;
    }

    public User getUser1() {
        return user1;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    @Override
    public String toString() {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        return g.toJson(this);
    }
}
