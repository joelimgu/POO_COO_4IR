package org.example.model.conversation;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class User {
    private final String pseudo;
    private final UUID uuid;

    public User(@NotNull String pseudo) {
        this.pseudo = pseudo;
        this.uuid = UUID.randomUUID(); // uuidv4
    }

    public User(@NotNull String pseudo, UUID uuid) {
        this.pseudo = pseudo;
        this.uuid = uuid;
    }


    public String getPseudo() {
        return pseudo;
    }

    public UUID getUuid() {
        return uuid;
    }
}
