package com.rtbytez.client;

public class ConnectSanitizedInput {
    private String host;
    private String username;
    private int port;
    public ConnectSanitizedInput(String host, int port, String username){
        this.host = host;
        this.username = username;
        this.port = port;
    }
}
