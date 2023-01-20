package org.example.services;

import org.example.model.communication.server.HTTPServer;
import org.example.model.communication.server.UDPReceive;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.User;

import java.util.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SessionService {
    private static SessionService instance ;
    private ConnectedUser m_localUser;
    private ArrayList<ConnectedUser> m_list = new ArrayList<ConnectedUser>();

    //private List<ConnectedUser> connectedUsers = new ArrayList<>();
    private volatile HashMap<UUID, ConnectedUser> usersConnected = new HashMap<>();

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
    synchronized public static SessionService getInstance(){
        if (instance == null){
            synchronized(SessionService.class) {
                if (instance == null) {
                    SessionService.instance = new SessionService();
                }
            }
        }
        return SessionService.instance;
    }


    synchronized public User getM_localUser() {
        return m_localUser;
    }

    synchronized public void setM_localUser(ConnectedUser m_localUser) {
        this.m_localUser = m_localUser;
    }

    synchronized public void setLocalIP(String IP) {
        this.m_localUser.setIP(IP);
    }

    synchronized public void setNb_connectedUser(int x){
        this.nb_connectedUser = x;
    }
    synchronized public int getNb_connectedUser() {
        return nb_connectedUser;
    }

    synchronized public void setHttpServer(HTTPServer httpServer) {
        this.httpServer = httpServer;
    }

    public HTTPServer getHttpServer() {
        return this.httpServer;
    }

    public void setUDPServer(UDPReceive u) {
        this.UDPServer = u;
    }

    synchronized public UDPReceive getUDPServer() {
        return this.UDPServer;
    }

    /** Order os not guaranteed
     * @return ConnectedUsers
     */
    synchronized public List<ConnectedUser> getConnectedUsers() {
        //List<ConnectedUser> c = new ArrayList<>(this.connectedUsers);
        HashMap<UUID, ConnectedUser> c = new HashMap<>(this.usersConnected);
        c.put(this.m_localUser.getUuid(), this.m_localUser);
        return new ArrayList<>(c.values());
    }

    synchronized public void removeConnectedUser(ConnectedUser u) {
        this.usersConnected.remove(u);
    }

    synchronized public void addConnectedUser(ConnectedUser u) {
        System.out.println("New user added : " + u.getPseudo());

        AtomicBoolean isInConnectedUsersList = new AtomicBoolean(false);
       /* for (ConnectedUser cu : usersConnected) {
            if (cu.getUuid() == u.getUuid()) {
                isInConnectedUsersList = true;
            }
        }
        */
        usersConnected.forEach(
                ((uuid, connectedUser) -> {
                    if (uuid == u.getUuid()) {
                        isInConnectedUsersList.set(true);
                    }
                })
        );

        if (!isInConnectedUsersList.get()) {
            //this.connectedUsers.add(u);
            this.usersConnected.put(u.getUuid(), u);
        }
    }

    public synchronized List<ConnectedUser> getRemoteConnectedUsers() {
        return new ArrayList<>(this.usersConnected.values());
    }

    synchronized public ConnectedUser deleteConnectedUserByName(String pseudo) {
        /*ConnectedUser u = this.connectedUsers.stream().filter((c) -> c.getPseudo().equals(pseudo))
                .collect(Collectors.toList()).get(0);
        this.connectedUsers.remove(u);
        return u;*/
        
        AtomicReference<ConnectedUser> u = new AtomicReference<>();

        usersConnected.forEach(
                ((uuid, connectedUser) ->
                {
                    if (connectedUser.getPseudo().equals(pseudo)) {
                        u.set(connectedUser);
                    }
                })
        );

        if (u.get() != null)
            usersConnected.remove(u.get().getUuid());

        return u.get();
    }

    public int getUdp_port() {
        return udp_port;
    }

    public int getHttp_port() {
        return http_port;
    }

}
