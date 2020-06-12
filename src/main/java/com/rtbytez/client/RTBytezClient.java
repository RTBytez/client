package com.rtbytez.client;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.rtbytez.client.editor.DocumentChangeHandler;
import com.rtbytez.client.editor.VFSEventHandler;
import com.rtbytez.client.file.LineMapper;
import com.rtbytez.client.socket.ConnectionData;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.client.trackers.FileModTracker;
import com.rtbytez.client.util.Functions;
import com.rtbytez.common.comms.packets.room.request.RTPRoomRequestJoin;
import com.rtbytez.common.util.Console;

import java.util.Arrays;

public class RTBytezClient {

    private static RTBytezClient instance = null;
    private static boolean isInitialized = false;

    private final Project project;
    private final VFSEventHandler vfsEventHandler;
    private final DocumentChangeHandler documentChangeHandler;
    private final FileModTracker fileModTracker;
    private final LineMapper lineMapper;
    private final Peer peer;

    /**
     * Private-Constructor due to singleton instance.
     * RTBytezClient instance
     *
     * @see RTBytezClient#getInstance()
     */
    private RTBytezClient() {
        this.project = ProjectManager.getInstance().getOpenProjects()[0];
        this.vfsEventHandler = new VFSEventHandler();
        this.documentChangeHandler = new DocumentChangeHandler();
        this.fileModTracker = new FileModTracker();
        this.lineMapper = new LineMapper();
        this.peer = new Peer();
    }

    public static void dummy() {
        System.out.println(Arrays.toString(Functions.getFilePaths()));
        try {
            RTBytezClient.getInstance().getPeer().connect(new ConnectionData("127.0.0.1", 5623, "", ""));
            RTBytezClient.getInstance().getPeer().emit(new RTPRoomRequestJoin("room", "000000", ""));
            Functions.psiFileFromString("foo.bar.txt");
            //RTBytezClient.getInstance().getPeer().emit(new RTPRoomRequestJoin("room", "262764f", ""));

        } catch (Exception e) {
            e.printStackTrace();
        }
        Console.log("Ran dummy test code");
    }

    /**
     * Retrieve the RTBytezClient singleton instance
     *
     * @return The instance of the client
     */
    public static RTBytezClient getInstance() {
        if (!isInitialized) {
            isInitialized = true;
            instance = new RTBytezClient();
            instance.getVfsEventHandler().register();
            instance.getDocumentChangeHandler().register();
            return instance;
        }
        return instance;
    }

    public boolean isOperational() {
        return peer.isConnected();
    }

    /**
     * Retrieve the peer associated with this client
     */
    public Peer getPeer() {
        return peer;
    }

    /**
     * Retrieve the project that this client is initialized to
     */
    public Project getProject() {
        return project;
    }

    public VFSEventHandler getVfsEventHandler() {
        return vfsEventHandler;
    }

    public DocumentChangeHandler getDocumentChangeHandler() {
        return documentChangeHandler;
    }

    public FileModTracker getFileModTracker() {
        return fileModTracker;
    }

    public LineMapper getLineMapper() {
        return lineMapper;
    }
}
