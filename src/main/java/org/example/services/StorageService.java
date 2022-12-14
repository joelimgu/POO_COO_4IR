package org.example.services;

import org.example.model.conversation.Conversation;
import org.example.model.conversation.Message;
import org.example.model.conversation.User;
import org.jetbrains.annotations.NotNull;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * This class is a singleton, only one instance should exist at any time
 */
public class StorageService {

    private static StorageService instance;
    private final String storagePath;
    private final Connection dbConnexion;

    private final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

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
                "    sent_at date not null default CURRENT_TIMESTAMP,\n" +
                "    foreign key (sender) references users(uuid) on delete cascade,\n" +
                "    foreign key (receiver) references users(uuid) on delete cascade\n" +
                "    primary key(uuid)\n" +
                ");"
        );
//        PreparedStatement s = this.dbConnexion.prepareStatement(
//                "select * from main.messages where sender=?"
//        );
//        s.setString(1,"Joel");
//        String m = s.executeQuery().getString("uuid");
//        System.out.println("Query=" + m);
    }

    /**
     * @throws RuntimeException if cant connect tot the db at start
     * @param path where de db will be sored
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
        for (Message m: conversation.getMessages()) {
            this.save(m);
        }
    }

    public void save(@NotNull Message m) throws SQLException {
//        System.out.println(m);
        this.save(m.getSender());
        this.save(m.getReceiver());
        PreparedStatement p = this.dbConnexion.prepareStatement(
                "insert or replace into main.messages" +
                "(uuid, sender, receiver, text, sent_at)" +
                "values (?, ?, ?, ?, ?)"
        );
        p.setString(1, m.getUuid().toString());
        p.setString(2, m.getSender().getUuid().toString());
        p.setString(3, m.getReceiver().getUuid().toString());
        p.setString(4, m.getText());
        p.setString(5, m.getSendTime().toString());
        p.executeUpdate();
    }

    public void save(@NotNull User u) throws SQLException {
        PreparedStatement p = this.dbConnexion.prepareStatement(
                "insert or replace into main.users" +
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

    public List<Message> retrieveAllMessages() throws SQLException {
        String query = "select messages.uuid, messages.sender as sender_uuid, u2.pseudo as sender, messages.receiver as receiver_uuid, u.pseudo as receiver, messages.text, sent_at from main.messages " +
                "join users u on u.uuid = messages.receiver " +
                "join users u2 on u2.uuid = messages.sender;";
        ResultSet rs = this.dbConnexion.createStatement().executeQuery(query);
        return getMessageListFromResultSet(rs);
    }

    @NotNull
    private ArrayList<Message> getMessageListFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<Message> lsm = new ArrayList<>();
        if (rs.isClosed()) {
            return lsm;
        }
        do {
            lsm.add(getMessageFromResult(rs));
        } while (rs.next());
        return lsm;
    }

    @NotNull
    private Message getMessageFromResult(ResultSet rs) throws SQLException {
        User sender = new User(rs.getString("sender"), UUID.fromString(rs.getString("sender_uuid")));
        User receiver = new User(rs.getString("receiver"), UUID.fromString(rs.getString("receiver_uuid")));
        Date d;
        try {
            d = this.sqlDateFormat.parse(rs.getString("sent_at"));
        } catch (ParseException e) {
            String colorRed = "\u001B[31m";
            String colorReset = "\u001B[0m";
            System.out.println(colorRed + "[ERROR] " + e + colorReset );
            d = new Date();
        }
        return new Message(UUID.fromString(rs.getString("uuid")), sender, receiver, rs.getString("text"), d);
    }

    public Conversation getConversation(@NotNull User local, @NotNull User remote) throws SQLException {
        String query = "select  messages.uuid, messages.sender as sender_uuid, sender.pseudo as sender, messages.receiver as receiver_uuid, receiver.pseudo as receiver, messages.text, sent_at from main.messages\n" +
                "join users receiver on receiver.uuid=messages.receiver\n" +
                "join users sender on sender.uuid = messages.sender\n" +
                "where messages.sender is ? " +
                "or messages.receiver is ?" +
                "or messages.sender is ?" +
                "or messages.receiver is ?;";

        PreparedStatement p = this.dbConnexion.prepareStatement(query);
        p.setString(1,local.getUuid().toString());
        p.setString(2,local.getUuid().toString());
        p.setString(3,remote.getUuid().toString());
        p.setString(4,remote.getUuid().toString());
        System.out.println("query generated");
        ResultSet rs = p.executeQuery();
        List<Message> messages = getMessageListFromResultSet(rs);
        return new Conversation(remote, messages);
    }

    public String getPath() {
        return this.storagePath;
    }
}
