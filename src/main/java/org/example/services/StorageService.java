package org.example.services;

import org.example.model.conversation.Conversation;
import org.example.model.conversation.History;
import org.example.model.conversation.Message;
import org.example.model.conversation.User;
import org.jetbrains.annotations.NotNull;


import java.sql.*;
import java.util.Objects;

/**
 * This class is a singleton, only one instance should exist at any time
 */
public class StorageService {

    private static StorageService instance;
    private final String storagePath;
    private final Connection dbConnexion;

    private StorageService(String path) throws SQLException {
        this.storagePath = path;
        this.dbConnexion = DriverManager.getConnection("jdbc:sqlite:clavardage.db");
        Statement statement = this.dbConnexion.createStatement();
        statement.setQueryTimeout(5);  // set timeout to 5 sec.
        statement.executeUpdate("create table if not exists users (\n" +
                "   uuid varchar(36) not null,\n" +
                "   pseudo varchar(31) not null,\n" +
                "   last_seen date not null default CURRENT_TIMESTAMP,\n" +
                "   primary key(uuid)\n" +
                ");"
        );
        statement.executeUpdate("create table if not exists messages (\n" +
                "    uuid varchar(36) not null,\n" +
                "    sender sender_id not null,\n" +
                "    receiver receiver_id not null,\n" +
                "    text varchar(2000) not null,\n" +
                "    send_at date not null default CURRENT_TIMESTAMP,\n" +
                "    foreign key (sender) references users(uuid) on delete cascade,\n" +
                "    foreign key (receiver) references users(uuid) on delete cascade\n" +
                "    primary key(uuid)\n" +
                ");"
        );
//        PreparedStatement s = this.dbConnexion.prepareStatement(
//                "select * from messages where sender=?"
//        );
//        s.setString(1,"Joel");
//        String m = s.executeQuery().getString("uuid");
//        System.out.println("Query=" + m);
    }

    /**
     * @throws RuntimeException if cant connect tot the db at start
     * @param path where de db will be sored
     * @return
     */
    public static StorageService StorageService(String path) {
        if(StorageService.instance == null) {
            try {
                StorageService.instance = new StorageService(path);
            } catch (SQLException e) {
                throw new RuntimeException("Can't connect to the DB",e);
            }
        } else if(!Objects.equals(path, StorageService.instance.storagePath)) {
            throw new IllegalArgumentException("There can only be one storage location for the app");
        }
        return StorageService.instance;
    }

    public void save(@NotNull Conversation conversation) throws SQLException {
        for (History h: conversation.getHistories()) {
            for (Message m: h) {
                this.save(m);
            }
        }
    }

    public void save(@NotNull Message m) throws SQLException {
        System.out.println(m);
        this.save(m.getSender());
        this.save(m.getReceiver());
        PreparedStatement p = this.dbConnexion.prepareStatement("insert into main.messages " +
                "(uuid, sender, receiver, text, send_at)\n" +
                "values (?, ?, ?, ?, ?)" +
                "where not exists (select * from main.messages where uuid=...;"); // todo
        p.setString(1, m.getUuid().toString());
        p.setString(2, m.getSender().getUuid().toString());
        p.setString(3, m.getReceiver().getUuid().toString());
        p.setString(4, m.getText());
        p.setString(5, m.getSendTime().toString());
        p.executeUpdate();
    }

    public void save(@NotNull User u) throws SQLException {
        PreparedStatement p = this.dbConnexion.prepareStatement(
                "insert into main.users" +
                "(uuid, pseudo, last_seen)\n" +
                "values (?, ?, ?);");
        p.setString(1, u.getUuid().toString());
        p.setString(2, u.getPseudo());
        p.setString(3, (new java.util.Date()).toString());
        p.executeUpdate();
    }

//    public saveMessage(Message m) {
////        this.dbConnexion.prepareStatement("")
//    }

//    public ArrayList<Conversation> retrieveAll() {
//
//    }

    public String getPath() {
        return this.storagePath;
    }
}
