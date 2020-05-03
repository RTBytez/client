package com.rtbytez.client;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.rtbytez.client.editor.PSIChangeEventHandler;
import com.rtbytez.client.editor.VFSEventHandler;
import com.rtbytez.client.socket.Peer;

public class RTBytezClient {

    private static RTBytezClient instance = null;
    private static boolean isInitialized = false;

    private final Project project;
    private final VFSEventHandler vfsEventHandler;
    private final PSIChangeEventHandler psiChangeEventHandler;
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
        this.psiChangeEventHandler = new PSIChangeEventHandler();
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
            instance.getPsiChangeEventHandler().register();
            return instance;
        }
        return instance;
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

    public PSIChangeEventHandler getPsiChangeEventHandler() {
        return psiChangeEventHandler;
    }
}
