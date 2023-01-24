package me.server.model;

import java.util.UUID;

public class Log {

    private UUID uuid;

    private String log;

    public Log(UUID uuid, String log) {
        this.log = log;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLog() {
        return log;
    }
}
