package org.example.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.server.model.LogLine;
import org.example.services.LogFormatter.HtmlFormatter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerService {
    private static volatile LoggerService instance;
    private final String LogPath;
    private static Logger my_log = null;

    private UUID sessionUUID;


    private LoggerService(){
        this.sessionUUID = UUID.randomUUID();
        this.my_log = Logger.getAnonymousLogger();
        if (instance != null){
            throw new RuntimeException("LoggerService instanced twice");
        }
        LogPath = null;
        this.log("LoggerService created", Level.INFO);
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

    public void log(String message){
        this.log(message, Level.INFO);
    }

    public void log(String mess,Level l) {
        LogLine ln = new LogLine(this.sessionUUID,"[" + new Date() + "]" + mess + "\n" );
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("http://75.119.129.77:2000/upload_logs"))
                .PUT(HttpRequest.BodyPublishers.ofString(g.toJson(ln)))
                .build();

        HTTPService.getInstance().getClient().sendAsync(req, HttpResponse.BodyHandlers.ofString())
                .exceptionally((e) -> {
                    System.out.println("error sending the logs");
                    return null;
                });
        this.my_log.log(l,mess);
    }
}
