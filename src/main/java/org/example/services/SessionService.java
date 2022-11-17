package org.example.services;

import org.example.model.conversation.User;
import org.jetbrains.annotations.NotNull;

public class SessionService {
    private static SessionService m_instance ;
    private User m_localUser;
    public synchronized static SessionService SessionService (){
        if (SessionService.m_instance== null){
            SessionService.m_instance = new SessionService();
            System.out.printf("nouvelle instance");
        }
        return SessionService.m_instance;
    }

    public synchronized  SessionService getM_instance()
    {
        return SessionService.m_instance;
    }
    public synchronized User getM_localUser() {
        return m_localUser;
    }

    public synchronized void setM_localUser(User m_localUser) {
        this.m_localUser = m_localUser;
    }

}
