package com.rtbytez.client.socket;

public class ConnectionData {

    String host;
    int port;
    String username;
    String passwordHash;

    public ConnectionData(String host, int port, String username, String passwordHash) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
