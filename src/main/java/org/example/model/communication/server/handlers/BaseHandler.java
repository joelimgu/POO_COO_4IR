package org.example.model.communication.server.handlers;

import org.example.model.communication.server.HTTPServer;
import org.jetbrains.annotations.NotNull;

public class BaseHandler {
    protected HTTPServer httpServer;
    public BaseHandler(@NotNull HTTPServer httpServer) {
        this.httpServer = httpServer;
    }
}
