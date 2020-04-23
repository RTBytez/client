package com.rtbytez.client.socketio;

public abstract class ClientEventHandler {
    /**
     * TODO: Document
     *
     * @param header
     * @param client
     */
    public abstract void exec(String header, SocketClient client);
}
