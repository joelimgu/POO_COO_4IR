package me.server;

import com.sun.net.httpserver.HttpServer;
import me.server.handlers.UploadLogs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HTTPServer {
    private final int port;
    public HTTPServer(int port) throws IOException {
        this.port = port;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 1000);

        server.createContext("/upload_logs", new UploadLogs());

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        server.setExecutor(threadPoolExecutor);
        server.start();

        System.out.println(" Server started on port " + port);
    }

    public int getPort() {
        return port;
    }
}
