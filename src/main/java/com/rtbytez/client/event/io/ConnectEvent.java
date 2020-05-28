package com.rtbytez.client.event.io;

import com.rtbytez.client.event.SocketEventHandler;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.client.socket.SocketStatus;
import com.rtbytez.common.comms.packets.info.request.RTPInfoRequestPeerId;
import com.rtbytez.common.comms.packets.info.request.RTPInfoRequestServerInstanceId;
import com.rtbytez.common.util.Console;

public class ConnectEvent extends SocketEventHandler {

    public void exec(Peer peer) {
        Console.log("Socket", "Connected to server");
        peer.setStatus(SocketStatus.CONNECTED);
        peer.emit(new RTPInfoRequestPeerId("info"));
        peer.emit(new RTPInfoRequestServerInstanceId("info"));
    }
}
