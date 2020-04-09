package com.rtbytez.client;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

public class RTBytezClient {

    private static RTBytezClient instance = null;
    private static boolean isInitialized = false;

    private SocketClient socketClient;
    private RTEventHandler eventHandler;
    private Project project;

    /**
     * Private-Constructor due to singleton instance.
     * RTBytezClient instance
     *
     * @see RTBytezClient#getInstance()
     */
    private RTBytezClient() {
        this.project = ProjectManager.getInstance().getOpenProjects()[0];
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
            instance.init();
            return instance;
        }
        return instance;
    }

    private void init() {
        Console.log("ClientInstance", "Initializing");
        this.socketClient = new SocketClient();
        this.eventHandler = new RTEventHandler();
        Console.log("ClientInstance", "Finished initializing!");
    }

    /**
     * Retrieve the socket client that is responsible for communication between the client and server
     */
    public SocketClient getSocketClient() {
        return socketClient;
    }

    /**
     * Retrieve the project that this client is initialized to
     */
    public Project getProject() {
        return project;
    }

    /**
     * Retrieve the event handler responsible for socket events
     */
    public RTEventHandler getEventHandler() {
        return eventHandler;
    }
}
