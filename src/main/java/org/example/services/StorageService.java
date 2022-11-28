package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.conversation.Conversation;
import org.example.model.conversation.History;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * This class is a singleton, only one instance should exist at any time
 */
public class StorageService {

    private static volatile StorageService instance;
    private final String storagePath;

    private StorageService(String path) {
        this.storagePath = path;
    }

    public static StorageService getInstance(String path) {
        if (instance == null){
            synchronized(SessionService.class) {
                if (instance == null) {
                    StorageService.instance = new StorageService(path);
                }
            }
        } else if(!Objects.equals(path, StorageService.instance.storagePath)) {
            throw new IllegalArgumentException("There can only be one storage location for the app");
        }
        return StorageService.instance;
    }

    public void save(@NotNull Conversation conversation) throws IOException {
        File directoryPath = new File(this.storagePath);
        System.out.println(Arrays.toString(directoryPath.list()));
        System.out.println(directoryPath.mkdir()); // create the .clavardage dir if it does not exist
        File conversations = new File(this.storagePath + "/conversations");
        conversations.mkdir();
        String historyPath = this.storagePath + "/conversations" + "/" + conversation.getUser().getUuid().toString();
        File userHistory = new File(historyPath);
        userHistory.mkdir();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for (History h : conversation.getHistories()) {
            File f = new File(historyPath + "/" + h.getFormattedDate() + ".json");
            f.createNewFile();
            try (FileWriter fw = new FileWriter(f)) {
                fw.write(gson.toJson(h));
            } catch (IOException e) {
                System.out.println("cant write to file");
            }
        }
    }

    public String getPath() {
        return this.storagePath;
    }
}
