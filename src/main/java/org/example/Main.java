package org.example;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.server.HTTPServer;
import org.example.controler.ListenersInit;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.*;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.model.conversation.ConnectedUser;
import org.example.model.conversation.Conversation;
import org.example.model.conversation.Message;
import org.example.model.conversation.User;
import org.example.services.HTTPService;
import org.example.services.SessionService;
import org.example.services.StorageService;
import org.example.view.LoginApplication;
import org.example.view.LoginController;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
//        if (true) {
//            return;
//        }
        System.out.println(Arrays.toString(args));
        if (Arrays.asList(args).contains("no-gui")) {
            System.out.println("Running with no gui");
            ListenersInit.startServers();
            return;
        } else if(Arrays.asList(args).contains("server")) {
            try {
                me.server.HTTPServer h = new HTTPServer(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
//        User user = new User("admin",  UUID.fromString("e6c593ee-cd77-416c-a06e-55edf2597b09"));
//        try {
//            StorageService.getInstance().updatePseudo(user, "test");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        LoginApplication.main(args);

//            System.out.println();
//        List<String> ips = new ArrayList<>();
//        try {
//            Enumeration<NetworkInterface> nics = NetworkInterface
//                    .getNetworkInterfaces();
//            while (nics.hasMoreElements()) {
//                NetworkInterface nic = nics.nextElement();
//                Enumeration<InetAddress> addrs = nic.getInetAddresses();
//                while (addrs.hasMoreElements()) {
//                    InetAddress addr = addrs.nextElement();
//                    ips.add(addr.getHostAddress());
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//        }
//        String ip = ips.stream().filter((s) -> s.startsWith("10.") || s.contains("192.")).toList().get(0);
//        System.out.println("IPs: " + ip);
////        new HTTPServer(8001);
//        Dotenv dotenv = Dotenv.configure().load();
//        // Get the directory where data will be stored, either configured on the .env or tmp by default
//        String dataDirectory = dotenv.get("SAVES_DIR", System.getProperty("java.io.tmpdir")) + "/.clavardage";
//
//        StorageService storage = StorageService.StorageService(dataDirectory);
//        ArrayList<Message> ms = new ArrayList<>();
//        ms.add(new Message(new User("Louis"), "coucou"));
//        ArrayList<History> hs = new ArrayList<>();
//        hs.add(new History(new Date(), ms));
//        hs.add(new History("05-2021", ms));
//        Conversation c = new Conversation(new User("Joel"), hs);
//        storage.save(c);
    }
}
