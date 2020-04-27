package com.rtbytez.client.event.io;

import com.rtbytez.client.event.SocketEventHandler;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.client.socket.SocketStatus;
import com.rtbytez.common.util.Console;

public class DisconnectedEvent extends SocketEventHandler {
    @Override
    public void exec(Peer peer) {
        Console.log("Socket", "Disconnected from server");
        peer.setStatus(SocketStatus.DISCONNECTED);
    }
}
