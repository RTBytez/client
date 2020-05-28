package com.rtbytez.client.event;

import com.rtbytez.client.socket.Peer;
import com.rtbytez.common.comms.packets.RTPacket;

public abstract class PacketEventHandler {

    public abstract void exec(Peer peer, RTPacket packet);

}
