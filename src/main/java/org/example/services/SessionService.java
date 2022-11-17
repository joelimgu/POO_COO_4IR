package org.example.services;

import org.example.model.conversation.User;
import org.jetbrains.annotations.NotNull;

public class SessionService {
    private static SessionService m_instance ;
    private User m_localUser;
    public static SessionService SessionService (){
        if (m_instance == null){
            SessionService.m_instance = new SessionService();
        }
        return SessionService.m_instance;
    }


    public User getM_localUser() {
        return m_localUser;
    }

    public void setM_localUser(User m_localUser) {
        this.m_localUser = m_localUser;
    }

}
