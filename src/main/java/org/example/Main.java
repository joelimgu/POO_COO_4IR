package org.example;

import org.example.model.communication.server.HTTPServer;

import java.io.IOException;
import io.github.cdimascio.dotenv.Dotenv;
public class Main {
    public static void main(String[] args) throws IOException {

        new HTTPServer(8001);
        Dotenv dotenv = Dotenv.configure().load();

        // Get the directory where data will be stored, either configured on the .env or tmp by default
        String dataDirectory = dotenv.get("SAVES_DIR", System.getProperty("java.io.tmpdir")) + "/.clavardage/";

    }
}
