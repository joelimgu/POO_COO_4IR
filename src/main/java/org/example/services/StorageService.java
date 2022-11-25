package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.conversation.Conversation;
import org.example.model.conversation.History;
import org.example.model.conversation.Message;
import org.example.model.conversation.User;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        statement.executeUpdate("create table if not exists users (\n" +
                "   uuid varchar(36),\n" +
                "   pseudo varchar(31),\n" +
                "   last_seen date,\n" +
                "   primary key(uuid)\n" +
                ");"
        );
        statement.executeUpdate("create table messages (\n" +
                "    uuid varchar(36),\n" +
                "    sender sender_id,\n" +
                "    receiver receiver_id,\n" +
                "    text varchar(2000),\n" +
                "    send_at date,\n" +
                "    foreign key (sender) references users(uuid),\n" +
                "    foreign key (receiver) references users(uuid)\n" +
                ");"
        );
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

    public void save(@NotNull Conversation conversation) throws IOException {

    }

//    public ArrayList<Conversation> retrieveAll() {
//
//    }

    public String getPath() {
        return this.storagePath;
    }
}
