package org.example.model.conversation;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conversation implements Serializable {

    private final List<History> histories = new ArrayList<>();

    private final User user;

    public Conversation (@NotNull User user) {
        this.user = user;
    }
}
