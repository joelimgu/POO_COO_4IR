package org.example.controler.events;

import com.sun.net.httpserver.HttpExchange;

/**
 * Emitted when the HTTP server has responded to a request
 * the String argument is the body of the request
 */
public class HTTPRequestEvent extends CustomEvent<String> {

}
