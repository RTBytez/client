package com.rtbytez.client.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.rtbytez.client.ConnectSanitizedInput;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.client.ui.dialogs.ConnectDialog;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class URIGetter extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        System.out.println("Point A");
        RTBytezClient client = RTBytezClient.getInstance();
        CredentialsGetter credentialsGetter = new CredentialsGetter();
        ConnectSanitizedInput input = retrieveURI();
        if (input != null) {
            ArrayList<String> credentials = credentialsGetter.retrieveCredentials();
            input.setUsername(credentials.get(0));
            input.setPassword(credentials.get(1));
        }
    }

    public ConnectSanitizedInput retrieveURI() {
        ConnectDialog dialog = new ConnectDialog();
        boolean ok = dialog.showAndGet(); // Hangs
        if (ok) {
            return dialog.getUriText();
        } else {
            return null;
        }
    }
}
