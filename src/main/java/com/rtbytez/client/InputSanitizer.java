package com.rtbytez.client;

public class InputSanitizer {
    private String username = null;
    private String host = null;
    private String port = null;

    public ConnectSanitizedInput sanitizeInputs(String input) {
        String s = input.substring(10, input.length() - 1) + input.charAt(input.length() - 1);
        String[] creds = s.split("@");
        if (creds.length == 2) {
            username = creds[0];
            creds = creds[1].split(":");
            if (creds.length == 2) {
                host = creds[0];
                port = creds[1];
            } else {
                host = creds[0];
            }
        } else {
            creds = creds[0].split(":");
            if (creds.length == 2) {
                port = creds[1];
            }
            host = creds[0];
        }
        return new ConnectSanitizedInput(host, port, username);
    }


}

