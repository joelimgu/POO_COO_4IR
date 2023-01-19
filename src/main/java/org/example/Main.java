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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.model.conversation.Conversation;
import org.example.model.conversation.History;
import org.example.model.conversation.Message;
import org.example.model.conversation.User;
import org.example.services.StorageService;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws IOException, ParseException, InterruptedException {

        new HTTPServer(12345);
    }
}
