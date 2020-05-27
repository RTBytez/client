package com.rtbytez.client.event.handlers;

import com.intellij.openapi.editor.Document;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.client.event.PacketEventHandler;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.file.broadcasts.RTPFileAddLine;
import com.rtbytez.common.comms.packets.file.broadcasts.RTPFileModifyLine;
import com.rtbytez.common.comms.packets.file.broadcasts.RTPFileRemoveLine;
import com.rtbytez.common.comms.packets.file.request.RTPFileRequestRetrieve;
import com.rtbytez.common.comms.packets.file.response.RTPFileHash;
import com.rtbytez.common.comms.packets.file.response.RTPFileRetrieve;
import com.rtbytez.common.util.Console;
import org.apache.commons.codec.digest.DigestUtils;

import static com.rtbytez.client.util.Functions.*;

public class FileEventHandler extends PacketEventHandler {
    @Override
    public void exec(Peer peer, RTPacket packet) {
        RTBytezClient client = RTBytezClient.getInstance();
        if (packet instanceof RTPFileModifyLine) {
            RTPFileModifyLine rtp = (RTPFileModifyLine) packet;
            replace(rtp.getFilePath(), client.getLineMapper().lineNumberOf(rtp.getFilePath(), rtp.getLineId()), rtp.getText());
        }

        if (packet instanceof RTPFileAddLine) {
            RTPFileAddLine rtp = (RTPFileAddLine) packet;
            client.getLineMapper().addLine(rtp.getFilePath(), rtp.getLineId(), rtp.getLineNumber());
            addLine(rtp.getFilePath(), rtp.getLineNumber());
        }

        if (packet instanceof RTPFileRemoveLine) {
            RTPFileRemoveLine rtp = (RTPFileRemoveLine) packet;
            removeLine(rtp.getFilePath(), client.getLineMapper().lineNumberOf(rtp.getFilePath(), rtp.getLineId()));
            client.getLineMapper().removeLine(rtp.getFilePath(), rtp.getLineId());
        }

        if (packet instanceof RTPFileHash) {
            RTPFileHash rtpFileHash = (RTPFileHash) packet;
            Document document = getDocument(rtpFileHash.getFilePath());
            String hash = "";
            if (document != null) {
                hash = DigestUtils.sha1Hex(document.getText());
            }
            if (!hash.equals(rtpFileHash.getHash())) {
                Console.log("HASH", "Desynced with file: " + rtpFileHash.getFilePath());
                peer.emit(new RTPFileRequestRetrieve("file", rtpFileHash.getFilePath()));
            }
        }

        if (packet instanceof RTPFileRetrieve) {
            //TODO: Wait for Functions.getPsiFile() to return a psi file or creates one and then returns one if non exists at location
        }
    }
}
