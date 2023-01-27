package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.services.LoggerService;

import java.io.IOException;
import java.io.OutputStream;

public class MyHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        LoggerService.getInstance().log("Request received");
        String requestParamValue = null;
        if ("GET".equals(httpExchange.getRequestMethod())) {
            this.handleResponse(httpExchange, requestParamValue);
//            requestParamValue = handleGetRequest(httpExchange);
        }
//        } else if ("POST".equals(httpExchange)) {
//            requestParamValue = this.handlePostRequest(httpExchange);
//        }
//        this.handleResponse(httpExchange, requestParamValue);
    }
    // extract queryparams from url in deeded
    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.
        getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {
        OutputStream outputStream = httpExchange.getResponseBody();

        // encode HTML content
        String htmlResponse = "Hello World";

        // this line is a must
        httpExchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
