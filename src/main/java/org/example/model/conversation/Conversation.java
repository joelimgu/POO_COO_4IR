package org.example.model.conversation;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conversation implements Serializable {

    private List<History> histories = new ArrayList<>();

    private final User user;

    public Conversation (@NotNull User user) {
        this.user = user;
    }

    // TODO, should be privade and be called from the other constructor
    public Conversation (@NotNull User user, @NotNull List<History> h) {
        this.user = user;
        this.histories = h;
    }

    public User getUser() {
        return user;
    }

    public List<History> getHistories() {
        return histories;
    }
}
