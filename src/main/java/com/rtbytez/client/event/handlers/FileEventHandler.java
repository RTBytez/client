package com.rtbytez.client.event.handlers;

import com.intellij.openapi.editor.Document;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.client.event.PacketEventHandler;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.client.util.Functions;
import com.rtbytez.common.comms.bundles.LineBundle;
import com.rtbytez.common.comms.packets.RTPacket;
import com.rtbytez.common.comms.packets.file.broadcasts.RTPFileAddLine;
import com.rtbytez.common.comms.packets.file.broadcasts.RTPFileCreate;
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
            return;
        }

        if (packet instanceof RTPFileAddLine) {
            RTPFileAddLine rtp = (RTPFileAddLine) packet;
            client.getLineMapper().addLine(rtp.getFilePath(), rtp.getLineId(), rtp.getLineNumber());
            if (!client.getPeer().getId().equals(rtp.getPeerId())) {
                addLine(rtp.getFilePath(), rtp.getLineNumber());
            }
            return;
        }

        if (packet instanceof RTPFileRemoveLine) {
            RTPFileRemoveLine rtp = (RTPFileRemoveLine) packet;
            if (!client.getPeer().getId().equals(rtp.getPeerId())) {
                removeLine(rtp.getFilePath(), client.getLineMapper().lineNumberOf(rtp.getFilePath(), rtp.getLineId()));
            }
            client.getLineMapper().removeLine(rtp.getFilePath(), rtp.getLineId());
            return;
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
            return;
        }

        if (packet instanceof RTPFileRetrieve) {
            RTPFileRetrieve rtpFileRetrieve = (RTPFileRetrieve) packet;
            Functions.psiFileFromString(rtpFileRetrieve.getFilePath());
            wipeFile(rtpFileRetrieve.getFilePath());
            for (LineBundle line : rtpFileRetrieve.getLines()) {
                client.getLineMapper().addLine(rtpFileRetrieve.getFilePath(), line.getLineId(), line.getLineNumber());
                addLine(rtpFileRetrieve.getFilePath(), line.getLineNumber() - 1, line.getText());
            }
            return;
        }

        if (packet instanceof RTPFileCreate) {
            RTPFileCreate rtpFileCreate = (RTPFileCreate) packet;
            psiFileFromString(rtpFileCreate.getFilePath());
        }
    }
}
