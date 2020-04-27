package com.rtbytez.client.event;

import com.rtbytez.client.socket.Peer;

public abstract class SocketEventHandler {

    public abstract void exec(Peer peer);

}
