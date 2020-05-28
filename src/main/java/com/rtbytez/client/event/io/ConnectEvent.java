package com.rtbytez.client.event.io;

import com.rtbytez.client.event.SocketEventHandler;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.client.socket.SocketStatus;
import com.rtbytez.common.comms.packets.auth.request.RTPAuthRequestLogin;
import com.rtbytez.common.util.Console;

public class ConnectEvent extends SocketEventHandler {

    /**
     * Called when the peer is connected to the server, but not yet authenticated
     */
    @Override
    public void exec(Peer peer) {
        Console.log("Socket", "Connected to server, now authenticating...");
        peer.setStatus(SocketStatus.AUTHENTICATING);
        peer.emit(new RTPAuthRequestLogin("auth", peer.getConnectionData().getUsername(), peer.getConnectionData().getPasswordHash()));
    }
}
