package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.conversation.Conversation;
import org.example.model.conversation.History;
import org.jetbrains.annotations.NotNull;

import java.io.*;
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

    private StorageService(String path) {
        this.storagePath = path;
    }

    public static StorageService StorageService(String path) {
        if(StorageService.instance == null) {
            StorageService.instance = new StorageService(path);
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
        String historyPath = this.storagePath + "/conversations" + "/" + conversation.getUser1().getUuid().toString();
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

    public ArrayList<Conversation> retrieveAll() {
        File directoryPath = new File(this.storagePath);
        System.out.println(Arrays.toString(directoryPath.list()));
        System.out.println(directoryPath.mkdir());
        File conversations = new File(this.storagePath + "/conversations");
        conversations.mkdir();
        System.out.println(Arrays.toString(conversations.list()));
        String[] existentConversationList =  conversations.list();
        if (existentConversationList == null) {
            return new ArrayList<>();
        }

        ArrayList<Conversation> retrievedConversations = new ArrayList<>();
        for (String uuid: existentConversationList) {
            File historiesPath = new File(conversations + "/" + uuid);
            String[] histories = historiesPath.list();
            System.out.println("Reading history: " + uuid + " | " + Arrays.toString(histories));
            if (histories == null) {
                return new ArrayList<>();
            }
            List<History> h2 = Arrays.stream(histories).map((h) -> {
                File f = new File(historiesPath + "/" + h);
                Gson g = new Gson();

                try {
                    return g.fromJson(new FileReader(f), History.class);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("unable to read a history file: " + f, e);
                }
            })
            .sorted()
            .toList();

            // TODO get user from 1st message construct conversation ans and SessionService and DONE!

        }
        return new ArrayList<>();
    }

    public String getPath() {
        return this.storagePath;
    }
}
