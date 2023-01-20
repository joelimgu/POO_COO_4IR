package org.example.model.communication.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.example.model.communication.server.handlers.*;
import org.example.model.CustomObservable;
import org.example.model.CustomObserver;
import org.example.model.communication.server.httpEvents.ConnectedUsersListReceived;
import org.example.model.communication.server.httpEvents.HTTPEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HTTPServer{

   // private final List<CustomObserver<HTTPEvent>> subscribers = new ArrayList<>();

    private List<CompletableFuture<HTTPEvent>> futureEvent = new ArrayList<>();
    int port = 0;
    public HTTPServer(int port) throws IOException {
        this.port = port;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 1000);

        server.createContext("/get_pseudo", new GetPseudo());
        server.createContext("/get_user", new getUserHandler());
        server.createContext("/end_session_handler",new EndSessionHandler(this));
        server.createContext("/ping",new PingHandler(this));
        server.createContext("/receive_message",new ReceiveMessageHandler(this));
        server.createContext("/receive_connected_users_list",new receiveConnectedUsersHandler(this));
        server.createContext("/get_self_ip",new getIPHandler(this));
        server.createContext("/disconnect",new DisconnectHandler(this));

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        server.setExecutor(threadPoolExecutor);
        server.start();

        System.out.println(" Server started on port " + port);
    }

    /**
     * Returns a simple HTTP response with the body with code 200 (Ok)
     */
    public static void sendResponse(HttpExchange httpExchange, String body) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();

        // this line is a must
        httpExchange.sendResponseHeaders(200, body.length());
        outputStream.write(body.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    public void notifyAllSubscribers(HTTPEvent c) {
        System.out.println("Notifying everyone HTTP");
        int currentIndex = futureEvent.size() - 1;
        while(futureEvent.size() != 0) {
            System.out.println("Notified: " + futureEvent.get(currentIndex) );
            futureEvent.get(currentIndex).complete(c);
            futureEvent.remove(currentIndex);
            currentIndex--;
        }
    }

   // @Override
   /* public int subscribe(CustomObserver<HTTPEvent> o) {
        this.subscribers.add(o);
        return this.subscribers.size() - 1;
        this.futureEvent.add(new CompletableFuture<HTTPEvent>(o));
    }*/

    synchronized public int addEventList(CompletableFuture<?> cf) {
        this.futureEvent.add((CompletableFuture<HTTPEvent>) cf);
        return this.futureEvent.size() - 1;
    }

   // @Override
    /*public CustomObserver<HTTPEvent> unsubscribe(int i) {
        CustomObserver<HTTPEvent> o = this.subscribers.get(i);
        this.subscribers.set(i, null);
        return o;
    }*/
}
