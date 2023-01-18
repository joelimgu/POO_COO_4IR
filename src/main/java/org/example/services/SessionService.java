package org.example.services;

import org.example.model.communication.server.HTTPServer;
import org.example.model.communication.server.UDPReceive;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.User;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SessionService {
    private static SessionService instance ;
    private ConnectedUser m_localUser;
    private List<ConnectedUser> m_list = Collections.synchronizedList(new ArrayList<>());

    private List<ConnectedUser> connectedUsers = Collections.synchronizedList(new ArrayList<>());

    private HTTPServer httpServer;

    private UDPReceive UDPServer;

    private int nb_connectedUser=0;

    private final int udp_port = 4000;
    private final int http_port = 3000;
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

    public void setM_localUser(ConnectedUser m_localUser) {
        this.m_localUser = m_localUser;
    }

    public void  setLocalIP(String IP) {
        this.m_localUser.setIP(IP);
    }

    public void setNb_connectedUser(int x){
        this.nb_connectedUser = x;
    }
    public int getNb_connectedUser() {
        return nb_connectedUser;
    }

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

    /** Order os not guaranteed
     * @return ConnectedUsers
     */
    synchronized public List<ConnectedUser> getConnectedUsers() {
        List<ConnectedUser> c = new ArrayList<>(this.connectedUsers);
        c.add(this.m_localUser);
        return c;
    }

    synchronized public List<ConnectedUser> getRemoteConnectedUsers() {
        return this.connectedUsers;
    }

    synchronized public void removeConnectedUser(ConnectedUser u) {
        this.connectedUsers.remove(u);
    }

    synchronized public void addConnectedUser(ConnectedUser u) {
        System.out.println("FAIT CHIER :)");

        boolean isInConnectedUsersList = false;
        for (ConnectedUser cu : connectedUsers) {
            if (cu.getUuid() == u.getUuid()) {
                isInConnectedUsersList = true;
            }
        }

        if (!isInConnectedUsersList) {
            this.connectedUsers.add(u);
        }
    }

    synchronized public ConnectedUser deleteConnectedUserByName(String pseudo) {
        ConnectedUser u = this.connectedUsers.stream().filter((c) -> c.getPseudo().equals(pseudo))
                .collect(Collectors.toList()).get(0);
        this.connectedUsers.remove(u);
        return u;
    }

    public int getUdp_port() {
        return udp_port;
    }

    public int getHttp_port() {
        return http_port;
    }
}
