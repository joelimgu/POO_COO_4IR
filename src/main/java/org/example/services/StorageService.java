package org.example.services;

import java.util.Objects;

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

    public String getPath() {
        return this.storagePath;
    }
}
