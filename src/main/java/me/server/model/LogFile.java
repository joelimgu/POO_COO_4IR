package me.server.model;

import java.util.Date;

public class LogFile {
    private String fileName;
    private Date lastModified;

    public LogFile(String fileName, long lastModified) {
        this.fileName = fileName;
        this.lastModified = new Date(lastModified);
    }

    public Date getLastModified() {
        return lastModified;
    }

    public String getFileName() {
        return fileName;
    }
}
