package com.rtbytez.client.socket;

import com.rtbytez.client.event.PacketEventHandler;
import com.rtbytez.client.event.SocketEventHandler;
import com.rtbytez.client.event.handlers.AuthEventHandler;
import com.rtbytez.client.event.handlers.FileEventHandler;
import com.rtbytez.client.event.handlers.InfoEventHandler;
import com.rtbytez.client.event.handlers.RoomEventHandler;
import com.rtbytez.client.event.io.*;
import com.rtbytez.client.util.Functions;
import com.rtbytez.common.comms.packets.PacketFactory;
import com.rtbytez.common.comms.packets.RTPacketRequest;
import com.rtbytez.common.comms.packets.info.request.RTPInfoRequestDisconnection;
import com.rtbytez.common.util.Console;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URI;
import java.net.URISyntaxException;

public class Peer {

    private Socket socket;
    private SocketStatus status;
    private String id;
    private ConnectionData connectionData;
    private PeerData peerData;

    public void connect(ConnectionData connectionData) throws URISyntaxException {
        if (this.status == null || this.status == SocketStatus.DISCONNECTED) {
            this.status = SocketStatus.CONNECTING;
            this.connectionData = connectionData;
            this.socket = IO.socket(new URI("http://" + connectionData.host + ":" + connectionData.port));
            this.peerData = new PeerData();
            registerAll();
            new Thread(() -> {
                Functions.safeSleep(5000L);
                if (!this.isConnected()) {
                    new ConnectingTimedOutEvent().exec(this);
                    socket.disconnect();
                    socket = null;
                    Console.log("Socket", "Timed Out Connecting");
                }
            }).start();
            socket.connect();
        } else {
            Console.log("Socket", "A connection process has already begun!");
        }
    }

    public void disconnect() {
        this.emit(new RTPInfoRequestDisconnection("info"));
        this.socket.disconnect();
    }

    public boolean isConnected() {
        return this.status == SocketStatus.CONNECTED;
    }

    public void register(String event, SocketEventHandler handler) {
        socket.on(event, args -> handler.exec(this));
    }

    public void register(String event, PacketEventHandler handler) {
        socket.on(event, args -> handler.exec(this, PacketFactory.createPacket(event, String.valueOf(args[0]))));
    }

    public void emit(RTPacketRequest packet) {
        socket.emit(packet.getHeader(), packet.toJsonString());
    }

    public SocketStatus getStatus() {
        return status;
    }

    public void setStatus(SocketStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PeerData getPeerData() {
        return peerData;
    }

    public ConnectionData getConnectionData() {
        return this.connectionData;
    }

    private void registerAll() {
        register("file", new FileEventHandler());
        register("room", new RoomEventHandler());
        register("info", new InfoEventHandler());
        register("auth", new AuthEventHandler());

        register(Socket.EVENT_CONNECT, new ConnectEvent());
        register(Socket.EVENT_CONNECTING, new ConnectingEvent());
        register(Socket.EVENT_RECONNECTING, new ReconnectingEvent());
        register(Socket.EVENT_DISCONNECT, new DisconnectedEvent());
    }

}
