package com.rtbytez.client.event.handlers;

import com.rtbytez.client.event.PacketEventHandler;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.info.response.RTPInfoPeerId;
import com.rtbytez.common.comms.packets.info.response.RTPInfoSecret;

public class InfoEventHandler extends PacketEventHandler {
    @Override
    public void exec(Peer peer, RTPacket packet) {
        if (packet instanceof RTPInfoPeerId) {
            RTPInfoPeerId rtpInfoPeerId = (RTPInfoPeerId) packet;
            peer.setId(rtpInfoPeerId.getId());
        }

        if (packet instanceof RTPInfoSecret) {
            RTPInfoSecret rtpInfoSecret = (RTPInfoSecret) packet;
            peer.setSecret(rtpInfoSecret.getSecret());
        }
    }
}
