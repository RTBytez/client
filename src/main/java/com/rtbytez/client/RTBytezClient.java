package com.rtbytez.client;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.rtbytez.common.util.Console;

public class RTBytezClient {

    private static RTBytezClient instance = null;
    private static boolean isInitialized = false;

    private final Project project;

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
        Console.log("ClientInstance", "Finished initializing!");
    }

    /**
     * Retrieve the project that this client is initialized to
     */
    public Project getProject() {
        return project;
    }
}
