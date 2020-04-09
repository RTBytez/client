package com.rtbytez.client;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class SocketClient {

    private Socket socket;
    private boolean isConnected = false;

    public void connect(String uri) throws URISyntaxException {
        socket = IO.socket(uri);
        socket.on(Socket.EVENT_CONNECT, args -> {
            Console.log("SOCKET", "Connected to " + uri);
            isConnected = true;
        });
        socket.on(Socket.EVENT_DISCONNECT, args -> {
            Console.log("SOCKET", "Disconnected from " + uri);
            isConnected = false;
        });
        socket.open();
    }

    /**
     * True if is connected to specified server
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Register an event with the socket event handler
     *
     * @param header  The header to add the handler to
     * @param handler A JSON Handler that will execute the method
     */
    public void register(String header, RTJsonHandler handler) {
        //TODO: Create
    }
}
