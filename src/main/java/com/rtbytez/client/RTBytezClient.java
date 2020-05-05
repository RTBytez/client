package com.rtbytez.client;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.rtbytez.client.editor.DocumentChangeHandler;
import com.rtbytez.client.editor.VFSEventHandler;
import com.rtbytez.client.file.Line;
import com.rtbytez.client.socket.Peer;
import com.rtbytez.client.trackers.FileModTracker;
import com.rtbytez.client.util.Functions;
import com.rtbytez.common.util.Console;

public class RTBytezClient {

    private static RTBytezClient instance = null;
    private static boolean isInitialized = false;

    private final Project project;
    private final VFSEventHandler vfsEventHandler;
    private final DocumentChangeHandler documentChangeHandler;
    private final FileModTracker fileModTracker;
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
        this.peer = new Peer();
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
            dummy();
            return instance;
        }
        return instance;
    }

    public static void dummy() {
        Line abc123 = new Line("123abc", 1, "abc123");
        Functions.replace("foo/bar.txt", abc123);
        Functions.psiFileFromString("obama.txt");
        Console.log("Ran dummy test code");
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
}
