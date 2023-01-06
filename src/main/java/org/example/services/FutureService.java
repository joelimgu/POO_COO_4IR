package org.example.services;

import org.example.model.conversation.ConnectedUser;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;

public class FutureService {

    private static FutureService instance;

    private SynchronousQueue<CompletableFuture<List<ConnectedUser>>> historyReception = new SynchronousQueue();

    private FutureService() {
        if (instance != null) {
            throw new RuntimeException("SessionService instanced twice");
        }
    }

    public static FutureService getInstance() {
        if (instance == null){
            synchronized(SessionService.class) {
                if (instance == null) {
                    FutureService.instance = new FutureService();
                }
            }
        }
        return FutureService.instance;
    }

    public void addTaskForHistoryReception(CompletableFuture<List<ConnectedUser>> task) {
        this.historyReception.add(task);
    }

    public void notifyHistoryReception(List<ConnectedUser> c) {
        this.historyReception.forEach((f) -> f.complete(c));
    }


}
