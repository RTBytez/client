package com.rtbytez.client.socket;

import com.rtbytez.common.comms.enums.ServerRole;

import java.util.ArrayList;
import java.util.List;

public class PeerData {

    String serverInstanceId = "";
    String username = "";
    String secret = "";
    int userId;
    ServerRole serverRole;

    String roomId = "";
    List<PeerObject> roomMembers = new ArrayList<>();

    public String getServerInstanceId() {
        return serverInstanceId;
    }

    public void setServerInstanceId(String serverInstanceId) {
        this.serverInstanceId = serverInstanceId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ServerRole getServerRole() {
        return serverRole;
    }

    public void setServerRole(ServerRole serverRole) {
        this.serverRole = serverRole;
    }

    public boolean isInRoom() {
        return !this.roomId.isEmpty();
    }

    public List<PeerObject> getRoomMembers() {
        return roomMembers;
    }
}
