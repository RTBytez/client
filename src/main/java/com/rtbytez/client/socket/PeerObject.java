package com.rtbytez.client.socket;

import com.rtbytez.common.comms.enums.RoomRole;
import com.rtbytez.common.comms.enums.ServerRole;

public class PeerObject {

    String peerId;
    String username;
    ServerRole serverRole;
    RoomRole roomRole;
    boolean isActive;
    boolean isOnline;

    public PeerObject(String peerId, String username, ServerRole serverRole, RoomRole roomRole, boolean isActive, boolean isOnline) {
        this.peerId = peerId;
        this.username = username;
        this.serverRole = serverRole;
        this.roomRole = roomRole;
        this.isActive = isActive;
        this.isOnline = isOnline;
    }

    public String getPeerId() {
        return peerId;
    }

    public String getUsername() {
        return username;
    }

    public ServerRole getServerRole() {
        return serverRole;
    }

    public RoomRole getRoomRole() {
        return roomRole;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
