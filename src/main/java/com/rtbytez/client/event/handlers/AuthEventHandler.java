package com.rtbytez.client.event.handlers;

import com.rtbytez.client.event.PacketEventHandler;
import com.rtbytez.client.event.io.AuthenticatedEvent;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.common.comms.enums.LoginResponseCode;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.auth.response.RTPAuthLoginResponse;

public class AuthEventHandler extends PacketEventHandler {
    @Override
    public void exec(Peer peer, RTPacket packet) {

        if (packet instanceof RTPAuthLoginResponse) {
            RTPAuthLoginResponse rtpAuthLoginResponse = (RTPAuthLoginResponse) packet;
            if (rtpAuthLoginResponse.getCode() == LoginResponseCode.SUCCESS) {
                new AuthenticatedEvent().exec(peer);
            } else if (rtpAuthLoginResponse.getCode() == LoginResponseCode.INVALID) {
                // Handle invalid


            } else {
                // Handle error
            }


        }
    }
}
