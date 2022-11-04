package org.example;

import org.example.model.communication.server.HTTPServer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import io.github.cdimascio.dotenv.Dotenv;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, InterruptedException {

//        new HTTPServer(8001);
        Dotenv dotenv = Dotenv.configure().load();

        // Get the directory where data will be stored, either configured on the .env or tmp by default
        String dataDirectory = dotenv.get("SAVES_DIR", System.getProperty("java.io.tmpdir")) + "/.clavardage/";
        DateFormat DFormat
                = new SimpleDateFormat("MM-yyyy");
        String d = "15-12-2022";
        Date n = new Date();
        String s = DFormat.format(n);
        sleep(1000);
        System.out.println(new Date());
        System.out.println( DFormat.parse(s));
    }
}
