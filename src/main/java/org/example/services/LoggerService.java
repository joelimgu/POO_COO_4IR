package org.example.services;

import org.example.services.LogFormatter.HtmlFormatter;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerService {
    private static volatile LoggerService instance;
    private final String LogPath;
    private static Logger my_log = null;

    private LoggerService(){
        this.my_log = Logger.getAnonymousLogger();
        if (instance != null){
            throw new RuntimeException("LoggerService instanced twice");
        }
        LogPath = null;
    }

    synchronized static public LoggerService getInstance(){
        if(instance == null){
            synchronized (LoggerService.class){
               if(instance == null){
                   instance = new LoggerService();
               }
            }
        }
        return instance;
    }

    public void setLogger(String path) {
        FileHandler fh;
        try {
            fh = new FileHandler(path);
            this.my_log.addHandler(fh);
            fh.setFormatter(new HtmlFormatter());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String mess,Level l){
        this.my_log.log(l,mess);
    }
}
