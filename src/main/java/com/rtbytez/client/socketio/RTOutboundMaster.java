package com.rtbytez.client.socketio;

import org.json.JSONException;
import org.json.JSONObject;

public class RTOutboundMaster {

    SocketClient socket;

    public void sendNewFile(String path) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("type", "create");
        json.put("path", path);
        socket.emit("file", json);
    }

    public void sendDeleteFile(String path) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("type", "delete");
        json.put("path", path);
        socket.emit("file", json);
    }

    public void sendRenameFile(String oldPath, String newPath) {

    }

}
