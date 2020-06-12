package com.rtbytez.client.actions;

import com.rtbytez.client.ui.dialogs.RoomJoinDialog;

public class RoomIDGetter {
    public String retrieveRoomID() {
        RoomJoinDialog roomJoinDialog = new RoomJoinDialog();
        roomJoinDialog.showAndGet();
        return roomJoinDialog.getRoomID();
    }
}
