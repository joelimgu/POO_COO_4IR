package org.example.services;

import org.example.model.communication.server.HTTPServer;
import org.example.model.communication.server.UDPReceive;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.User;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

public class SessionService {
    private static SessionService instance ;
    private User m_localUser;
    private ArrayList<ConnectedUser> m_list = new ArrayList<ConnectedUser>();

    private List<ConnectedUser> connectedUsers = new ArrayList<>();

    private HTTPServer httpServer;

    private UDPReceive UDPServer;

    private int nb_connectedUser=0;
    private SessionService() {
        if (instance != null) {
            throw new RuntimeException("SessionService instanced twice");
        }
    }
    public static SessionService getInstance(){
        if (instance == null){
            synchronized(SessionService.class) {
                if (instance == null) {
                    SessionService.instance = new SessionService();
                }
            }
        }
        return SessionService.instance;
    }


    public User getM_localUser() {
        return m_localUser;
    }

    synchronized public void setConnectedUsers(List<ConnectedUser> u) {
        this.connectedUsers = u;
    }
    public void setM_localUser(User m_localUser) {
        this.m_localUser = m_localUser;
    }

    public void setNb_connectedUser(int x){this.nb_connectedUser = x;}
    public int getNb_connectedUser() {return nb_connectedUser;}

    public void setHttpServer(HTTPServer httpServer) {
        this.httpServer = httpServer;
    }

    public HTTPServer getHttpServer() {
        return this.httpServer;
    }

    public void setUDPServer(UDPReceive u) {
        this.UDPServer = u;
    }

    public UDPReceive getUDPServer() {
        return this.UDPServer;
    }

    public List<ConnectedUser> getConnectedUsers() {
        return connectedUsers;
    }
}
