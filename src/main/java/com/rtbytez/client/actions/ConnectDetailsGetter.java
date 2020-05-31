package com.rtbytez.client.actions;

import com.rtbytez.client.ConnectSanitizedInput;

import java.util.ArrayList;

public class ConnectDetailsGetter {
    public ConnectSanitizedInput getConnectDetails() {
        CredentialsGetter credentialsGetter = new CredentialsGetter();
        URIGetter uriGetter = new URIGetter();
        ConnectSanitizedInput input = uriGetter.retrieveURI();
        ArrayList<String> credentials = credentialsGetter.retrieveCredentials();
        input.setUsername(credentials.get(0));
        input.setPassword(credentials.get(1));
        return input;
    }
}
