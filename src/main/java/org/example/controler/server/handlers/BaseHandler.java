package org.example.controler.server.handlers;

import org.example.controler.server.HTTPServer;
import org.jetbrains.annotations.NotNull;

public class BaseHandler {
    protected HTTPServer httpServer;
    public BaseHandler(@NotNull HTTPServer httpServer) {
        this.httpServer = httpServer;
    }
}
