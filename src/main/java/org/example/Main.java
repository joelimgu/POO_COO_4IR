package org.example;

import org.example.model.communication.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Server s = new Server(8001);
    }
}
