package org.example.services;

import org.example.model.conversation.User;

public class SessionService {
    private static SessionService m_instance ;
    private User m_localUser;
    public synchronized static SessionService SessionService (){
        if (SessionService.m_instance== null){
            try {
                SessionService.m_instance = new SessionService();
            } catch (InstantiationException e) {
                throw new RuntimeException("Tried to instantiate a service twice", e);
            }
            System.out.print("nouvelle instance");
        }
        return SessionService.m_instance;
    }

    public SessionService() throws InstantiationException {
        if (SessionService.m_instance != null) {
            throw new InstantiationException("Cannot create new instances of a Service");
        }

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
