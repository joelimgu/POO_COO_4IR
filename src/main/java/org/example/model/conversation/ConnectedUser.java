package org.example.model.conversation;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

import java.net.UnknownHostException;
import java.util.UUID;

public class ConnectedUser extends User {
    public String m_IP;
    public ConnectedUser(@NotNull String Pseudo,String IP)
    {
        super(Pseudo);
        this.m_IP = IP;
    }

    public void setIP(String IP) {
        this.m_IP = IP;
    }
    public ConnectedUser (@NotNull String Pseudo, UUID uuid, String IP)
    {
        super(Pseudo,uuid);
        this.m_IP = IP;
    }

    public String getIP() {
        return this.m_IP;
    }

    public User getUser() {
        return new User(this.getPseudo(), this.getUuid());
    }
}
