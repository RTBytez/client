package com.rtbytez.client.socketio;

import org.json.JSONObject;

public class Client {

    public static Client instance;

    public Client() {

    }

    public static Client getClient() {
        return instance;
    }

    public void emit(String header, JSONObject json) {

    }

    public void on(String header, ClientEventHandler handler) {
        handler.exec(header, this);
    }

}
