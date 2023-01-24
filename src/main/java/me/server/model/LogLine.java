package me.server.model;

import java.util.UUID;

public class LogLine {

    private UUID uuid;
    private String logLine;

    public LogLine(UUID session, String logLine) {
        this.logLine = logLine;
        this.uuid = session;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLogLine() {
        return logLine;
    }
}
