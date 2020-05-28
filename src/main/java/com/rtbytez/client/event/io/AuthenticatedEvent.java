package com.rtbytez.client.event.io;

import com.rtbytez.client.event.SocketEventHandler;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.client.socket.SocketStatus;
import com.rtbytez.common.comms.packets.info.request.RTPInfoRequestPeerInfo;
import com.rtbytez.common.comms.packets.info.request.RTPInfoRequestServerInstanceId;

public class AuthenticatedEvent extends SocketEventHandler {
    /**
     * Called when the peer is successfully authenticated with the server and is ready to send packets back and forth
     */
    @Override
    public void exec(Peer peer) {
        peer.setStatus(SocketStatus.CONNECTED);
        peer.emit(new RTPInfoRequestPeerInfo("info"));
        peer.emit(new RTPInfoRequestServerInstanceId("info"));
    }
}
