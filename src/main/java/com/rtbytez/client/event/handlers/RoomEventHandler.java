package com.rtbytez.client.event.handlers;

import com.intellij.openapi.application.ApplicationManager;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.client.actions.RoomCreateGetter;
import com.rtbytez.client.event.PacketEventHandler;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.room.broadcasts.RTPRoomLeave;
import com.rtbytez.common.comms.packets.room.broadcasts.RTPRoomRoleChange;
import com.rtbytez.common.comms.packets.room.response.RTPRoomJoined;

public class RoomEventHandler extends PacketEventHandler {
    @Override
    public void exec(Peer peer, RTPacket packet) {
        if (packet instanceof RTPRoomJoined) {
            RTPRoomJoined rtpRoomJoined = (RTPRoomJoined) packet;
            RTBytezClient.getInstance().getPeer().getPeerData().setRoomId(rtpRoomJoined.getRoomId());
            ApplicationManager.getApplication().invokeLater(() -> {
                RoomCreateGetter roomCreateGetter = new RoomCreateGetter();
                roomCreateGetter.showRoomID(rtpRoomJoined.getRoomId());

            });
        }
        if (packet instanceof RTPRoomLeave) {
            RTPRoomLeave rtpRoomLeave = (RTPRoomLeave) packet;
            if (rtpRoomLeave.getPeerId().equals(RTBytezClient.getInstance().getPeer().getId())) {
                RTBytezClient.getInstance().getPeer().getPeerData().setRoomId("");
            }
        }
        if (packet instanceof RTPRoomRoleChange) {
            RTPRoomRoleChange rtpRoomRoleChange = (RTPRoomRoleChange) packet;
            ApplicationManager.getApplication().invokeLater(() -> {
                RoomCreateGetter roomCreateGetter = new RoomCreateGetter();
                roomCreateGetter.showRoomID(rtpRoomRoleChange.getRoomId());
            });
        }
    }
}