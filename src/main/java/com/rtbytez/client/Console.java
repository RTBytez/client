package com.rtbytez.client;

public class Console {

    /**
     * Disabled Object Initialization due to this class being static-only
     */
    private Console() {

    }

    /**
     * Log a message to the console if debug mode is enabled
     *
     * @param message The message to log
     */
    public static void log(String message) {
        System.out.println(message);
    }

    /**
     * Log a message to the console with error context
     *
     * @param message The error message
     */
    public static void error(String message) {
        System.out.println("[Error] " + message);
    }

    /**
     * Log a message to the console with error title and context
     *
     * @param title   The location or title of the error
     * @param message The error message
     */
    public static void error(String title, String message) {
        System.out.println("[Error: " + title + "] " + message);
    }

    /**
     * Log a message to the console if debug mode is enabled
     *
     * @param title   The title or origin of the message
     * @param message The message to log
     */
    public static void log(String title, String message) {
        System.out.println("[" + title + "] " + message);
    }
}
