package com.rtbytez.client.socket;

import com.rtbytez.client.event.PacketEventHandler;
import com.rtbytez.client.event.SocketEventHandler;
import com.rtbytez.client.event.handlers.FileEventHandler;
import com.rtbytez.client.event.handlers.InfoEventHandler;
import com.rtbytez.client.event.handlers.RoomEventHandler;
import com.rtbytez.client.event.io.ConnectEvent;
import com.rtbytez.client.event.io.ConnectingEvent;
import com.rtbytez.client.event.io.DisconnectedEvent;
import com.rtbytez.client.event.io.ReconnectingEvent;
import com.rtbytez.client.util.Functions;
import com.rtbytez.common.comms.packets.PacketFactory;
import com.rtbytez.common.comms.packets.RTPacketRequest;
import com.rtbytez.common.comms.packets.auth.request.RTPAuthRequestLogin;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.apache.commons.codec.digest.DigestUtils;

import java.net.URI;
import java.net.URISyntaxException;

public class Peer {

    private Socket socket;
    private SocketStatus status;
    private String id;
    private String secret;

    public void connect(String uriString) throws URISyntaxException {
        URI uri = new URI(uriString);
        socket = IO.socket(uri);

        register("file", new FileEventHandler());
        register("room", new RoomEventHandler());
        register("info", new InfoEventHandler());

        register(Socket.EVENT_CONNECT, new ConnectEvent());
        register(Socket.EVENT_CONNECTING, new ConnectingEvent());
        register(Socket.EVENT_RECONNECTING, new ReconnectingEvent());
        register(Socket.EVENT_DISCONNECT, new DisconnectedEvent());

        socket.connect();
    }

    public void login(String username, String password, String host) {
        // URI checking should be done before we hit this point
        assert Functions.isValidURI("http://" + host);

        String passwordHash = DigestUtils.sha1Hex(password);
        register(Socket.EVENT_CONNECT, new SocketEventHandler() {
            @Override
            public void exec(Peer peer) {
                peer.emit(new RTPAuthRequestLogin("auth", username, passwordHash));
            }
        });

        try { //Make IDEs happy
            this.connect("http://" + host);
        } catch (Exception ignored) {
        }
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

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
