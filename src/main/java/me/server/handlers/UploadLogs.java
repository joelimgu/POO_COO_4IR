package me.server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import me.server.model.Log;
import me.server.model.LogFile;
import me.server.model.LogLine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UploadLogs implements HttpHandler {
    String path = ".";
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        if (exchange.getRequestMethod().equals("PUT")) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("received logs: " + body);
            try {
                LogLine lg = g.fromJson(body, LogLine.class);
                // save the body string to a file named "logs.txt"
                FileWriter fw = new FileWriter(path + "/" + lg.getUuid() + ".txt", true);
                System.out.println("writing " + lg.getLogLine() + " to " + lg.getUuid() + ".txt");
                fw.write(lg.getLogLine());
                fw.close();
            } catch (Exception e) {
                System.out.println("Error saving the logs");
                e.printStackTrace();
            }
            response = "Updated";
        } else if (exchange.getRequestMethod().equals("POST")) {
            String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("received log: " + body);
            try {
                Log lg = g.fromJson(body, Log.class);
                // save the body string to a file named "logs.txt"
                FileWriter fw = new FileWriter(path + "/" + lg.getUuid() + ".txt", false);
                fw.write(lg.getLog());
                fw.close();
            } catch (Exception e) {
                System.out.println("Error saving the logs");
                e.printStackTrace();
            }
            response = "Uploaded";
        }
        else if (exchange.getRequestMethod().equals("GET")) {
            String uri = exchange.getRequestURI().toString();
            String option = uri.split("/")[uri.split("/").length - 1];
            System.out.println(option);

            if (option.equals("list")) {
                // list all files in a directory
                List<String> files = new ArrayList<>();
                response = "Not logs found";
                try {
                    File[] f = new File(path).listFiles();
                    if (f != null) {
                        List<LogFile> lf = Stream.of(f)
                                .filter(file -> !file.isDirectory())
                                .map((file) -> {
                                    return new LogFile(file.getName(),file.lastModified());
                                }).collect(Collectors.toList());
                        response = g.toJson(lf);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // if you send a uuid you gent a log in return
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                try {
                    System.out.println("tring to get: " + body);
                    FileReader fr = new FileReader(path + "/" + option + ".txt");
                    StringBuilder sb = new StringBuilder();
                    int c;
                    while ((c = fr.read()) != -1) {
                        sb.append((char) c);
                    }
                    fr.close();
                    response = sb.toString();
                } catch (FileNotFoundException e) {
                    response = "Not Found";
                }
            }
        } else {
            response = "Not uploaded";
        }
        response += "\n";
        exchange.sendResponseHeaders(200, response.length());
        exchange.getResponseBody().write(response.getBytes());
        exchange.close();
    }
}

