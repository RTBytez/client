package com.rtbytez.client.event.handlers;

import com.rtbytez.client.event.PacketEventHandler;
import com.rtbytez.client.socket.ConnectionData;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.info.response.RTPInfoPeerInfo;
import com.rtbytez.common.comms.packets.info.response.RTPInfoSecret;
import com.rtbytez.common.comms.packets.info.response.RTPInfoServerInstanceId;

import java.net.URISyntaxException;

public class InfoEventHandler extends PacketEventHandler {
    @Override
    public void exec(Peer peer, RTPacket packet) {
        if (packet instanceof RTPInfoPeerInfo) {
            RTPInfoPeerInfo rtpInfoPeerId = (RTPInfoPeerInfo) packet;
            peer.setId(rtpInfoPeerId.getId());
            peer.getPeerData().setUserId(rtpInfoPeerId.getUserId());
            peer.getPeerData().setServerRole(rtpInfoPeerId.getServerRole());
            return;
        }

        if (packet instanceof RTPInfoSecret) {
            RTPInfoSecret rtpInfoSecret = (RTPInfoSecret) packet;
            peer.getPeerData().setSecret(rtpInfoSecret.getSecret());
            return;
        }

        if (packet instanceof RTPInfoServerInstanceId) {
            RTPInfoServerInstanceId rtpInfoServerInstanceId = (RTPInfoServerInstanceId) packet;
            if (peer.getPeerData().getServerInstanceId() == null || peer.getPeerData().getServerInstanceId().equals("")) {
                peer.getPeerData().setServerInstanceId(rtpInfoServerInstanceId.getServerInstanceId());
            } else {
                if (!peer.getPeerData().getServerInstanceId().equals(rtpInfoServerInstanceId.getServerInstanceId())) {
                    ConnectionData connectionData = peer.getConnectionData();
                    peer.disconnect();
                    try {
                        peer.connect(connectionData);
                    } catch (URISyntaxException ignored) {
                    }
                }
            }
            return;
        }
    }
}
