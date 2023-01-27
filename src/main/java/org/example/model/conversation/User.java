package org.example.model.conversation;

import com.google.gson.GsonBuilder;
import org.example.services.StorageService;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class User {
    private String pseudo;
    private final UUID uuid;

    public User(@NotNull String pseudo) {
        this.pseudo = pseudo;
        this.uuid = UUID.randomUUID(); // uuidv4
    }

    public User(@NotNull String pseudo, UUID uuid) {
        this.pseudo = pseudo;
        this.uuid = uuid;
    }

    public boolean equals(User u) {
        return this.pseudo.equals(u.pseudo) && this.uuid.equals(u.getUuid());
    }

    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public UUID getUuid() {
        return uuid;
    }

    public static boolean Is_unique(User e) throws SQLException {
        StorageService data_base = StorageService.getInstance("");
        List<User> m_list = data_base.getAllRegisteredUsers();
        for (User m_user : m_list) {
            if (m_user.getPseudo().equals(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
