package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 5);

        server.createContext("/test", new  MyHttpHandler());

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        server.setExecutor(threadPoolExecutor);

        server.start();

        System.out.println(" Server started on port 8001");


    }
}
